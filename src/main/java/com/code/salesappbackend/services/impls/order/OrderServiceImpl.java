package com.code.salesappbackend.services.impls.order;

import com.code.salesappbackend.dtos.requests.order.OrderDto;
import com.code.salesappbackend.dtos.requests.product.ProductOrderDto;
import com.code.salesappbackend.dtos.responses.socket.MessageResponse;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.exceptions.OutOfInStockException;
import com.code.salesappbackend.mappers.address.AddressMapper;
import com.code.salesappbackend.models.enums.*;
import com.code.salesappbackend.models.id_classes.UserVoucherId;
import com.code.salesappbackend.models.order.Order;
import com.code.salesappbackend.models.order.OrderDetail;
import com.code.salesappbackend.models.order.OrderVoucher;
import com.code.salesappbackend.models.product.Product;
import com.code.salesappbackend.models.product.ProductDetail;
import com.code.salesappbackend.models.product.ProductPrice;
import com.code.salesappbackend.models.socket.Notification;
import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.models.user.UserNotification;
import com.code.salesappbackend.models.user.UserVoucher;
import com.code.salesappbackend.models.voucher.Voucher;
import com.code.salesappbackend.models.voucher.VoucherUsages;
import com.code.salesappbackend.repositories.*;
import com.code.salesappbackend.repositories.order.OrderDetailRepository;
import com.code.salesappbackend.repositories.order.OrderRepository;
import com.code.salesappbackend.repositories.order.OrderVoucherRepository;
import com.code.salesappbackend.repositories.product.ProductDetailRepository;
import com.code.salesappbackend.repositories.product.ProductPriceRepository;
import com.code.salesappbackend.repositories.product.ProductRepository;
import com.code.salesappbackend.repositories.socket.NotificationRepository;
import com.code.salesappbackend.repositories.user.UserNotificationRepository;
import com.code.salesappbackend.repositories.user.UserRepository;
import com.code.salesappbackend.repositories.user.UserVoucherRepository;
import com.code.salesappbackend.repositories.voucher.VoucherRepository;
import com.code.salesappbackend.repositories.voucher.VoucherUsagesRepository;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {
    private VoucherRepository voucherRepository;
    private ProductPriceRepository productPriceRepository;
    private AddressMapper addressMapper;
    private ProductDetailRepository productDetailRepository;
    @Value("${delivery-price.express}")
    private Double expressPrice;
    @Value("${delivery-price.economy}")
    private Double economyPrice;
    private OrderDetailRepository orderDetailRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private UserVoucherRepository userVoucherRepository;
    private final VoucherUsagesRepository voucherUsagesRepository;
    private OrderRepository orderRepository;
    private NotificationRepository notificationRepository;
    private UserNotificationRepository userNotificationRepository;
    private SimpMessagingTemplate messagingTemplate;
    private OrderVoucherRepository orderVoucherRepository;

    public OrderServiceImpl(BaseRepository<Order, String> repository,
                            VoucherUsagesRepository voucherUsagesRepository) {
        super(repository, Order.class);
        this.voucherUsagesRepository = voucherUsagesRepository;
    }

    @Autowired
    public void setProductPriceRepository(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Autowired
    public void setVoucherRepository(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Autowired
    public void setAddressMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Autowired
    public void setProductDetailRepository(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }

    @Autowired
    public void setOrderDetailRepository(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setUserVoucherRepository(UserVoucherRepository userVoucherRepository) {
        this.userVoucherRepository = userVoucherRepository;
    }



    @Override
    @Transactional(rollbackFor = {DataNotFoundException.class, OutOfInStockException.class})
    public Order save(OrderDto orderDto) throws DataNotFoundException, OutOfInStockException {
        List<ProductOrderDto> productOrders = orderDto.getProductOrders();
        User user = userRepository.findByEmail(orderDto.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        double originalAmount = 0;
        OrderStatus orderStatus = OrderStatus.PENDING;
        if(orderDto.getPaymentMethod().equals(PaymentMethod.CC)) {
            orderStatus = OrderStatus.UNPAID;
        }
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .user(user)
                .orderDate(LocalDateTime.now())
                .orderStatus(orderStatus)
                .originalAmount(originalAmount)
                .paymentMethod(orderDto.getPaymentMethod())
                .deliveryMethod(orderDto.getDeliveryMethod())
                .phoneNumber(orderDto.getPhoneNumber())
                .buyerName(orderDto.getBuyerName())
                .note(orderDto.getNote())
                .deliveryAmount(
                        orderDto.getDeliveryMethod().equals(DeliveryMethod.EXPRESS) ? expressPrice : economyPrice)
                .address(addressMapper.addressDto2Address(orderDto.getAddress()))
                .build();
        order = super.save(order);
        originalAmount = handleAmount(productOrders, order, originalAmount);

        List<Long> vouchers = orderDto.getVouchers();
        if(vouchers != null) {
            for (Long voucherId : vouchers) {
                Voucher voucher = voucherRepository.findById(voucherId)
                        .orElseThrow(() -> new DataNotFoundException("Voucher not found"));
                UserVoucherId userVoucherId = new UserVoucherId(
                        user, voucher
                );
                if(voucher.getScope().equals(Scope.FOR_USER)) {
                    UserVoucher userVoucher = userVoucherRepository.findById(userVoucherId)
                            .orElseThrow(() -> new DataNotFoundException("UserVoucher not found"));
                    if(!userVoucher.isUsed()) {
                        double discountPrice = addVoucherDeliveryToOrder(originalAmount, voucher);
                        if(discountPrice > 0) {
                            userVoucher.setUsed(true);
                        }
                        if(voucher.getVoucherType().equals(VoucherType.FOR_DELIVERY)) {
                            double discount = order.getDeliveryAmount() - discountPrice;
                            if(discount < 0) {
                                discount = 0;
                            }
                            order.setDeliveryAmount(discount);
                        } else {
                            order.setDiscountedPrice(
                                    (order.getDiscountedPrice() == null ? 0 : order.getDiscountedPrice())
                                            + discountPrice);

                        }
                        userVoucherRepository.save(userVoucher);
                        OrderVoucher orderVoucher = new OrderVoucher();
                        orderVoucher.setOrder(order);
                        orderVoucher.setVoucher(voucher);
                        orderVoucherRepository.save(orderVoucher);
                    }
                } else {
                    Optional<VoucherUsages> voucherUsages = voucherUsagesRepository.findById(userVoucherId);
                    double discountPrice = addVoucherDeliveryToOrder(originalAmount, voucher);
                    if(voucherUsages.isEmpty()) {
                        if(voucher.getVoucherType().equals(VoucherType.FOR_DELIVERY)) {
                            double discount = order.getDeliveryAmount() - discountPrice;
                            if(discount < 0) {
                                discount = 0;
                            }
                            order.setDeliveryAmount(discount);
                        } else {
                            order.setDiscountedPrice(
                                    (order.getDiscountedPrice() == null ? 0 : order.getDiscountedPrice())
                                            + discountPrice);
                        }
                        if(discountPrice > 0) {
                            VoucherUsages voucherUsages1 = new VoucherUsages();
                            voucherUsages1.setVoucher(voucher);
                            voucherUsages1.setUser(user);
                            voucherUsages1.setUsagesDate(LocalDateTime.now());
                            voucherUsagesRepository.save(voucherUsages1);
                            OrderVoucher orderVoucher = new OrderVoucher();
                            orderVoucher.setOrder(order);
                            orderVoucher.setVoucher(voucher);
                            orderVoucherRepository.save(orderVoucher);
                        }
                    }

                }
            }
        }

        order.setDiscountedAmount((originalAmount + order.getDeliveryAmount())
                - (order.getDiscountedPrice() == null ? 0 : order.getDiscountedPrice()));
        order.setOriginalAmount(originalAmount);
        return super.save(order);
    }

    @Override
    public Order updateStatusOrder(String id, OrderStatus status) throws DataNotFoundException {
        if(!status.equals(OrderStatus.PAID)) {
            Order order = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order not found"));
            order.setOrderStatus(status);
            orderRepository.save(order);
            switch (status) {
                case PROCESSING:
                    handleNotification(order, "Đơn hàng " + order.getId() + " đã được xác nhận");
                    break;
                case SHIPPED:
                    handleNotification(order, "Đơn hàng " + order.getId() + " đã được giao");
                    break;
                case CANCELLED:
                    List<OrderVoucher> orderVouchers = orderVoucherRepository.findAllByOrderId(order.getId());
                    for(OrderVoucher orderVoucher : orderVouchers) {
                        Voucher voucher = orderVoucher.getVoucher();
                        if(voucher.getScope().equals(Scope.FOR_USER)) {
                            UserVoucher userVoucher = userVoucherRepository.findById(
                                    new UserVoucherId(order.getUser(), voucher)
                            ).orElseThrow(() -> new DataNotFoundException("UserVoucher not found"));
                            userVoucher.setUsed(false);
                            userVoucherRepository.save(userVoucher);
                        } else {
                            VoucherUsages voucherUsages = voucherUsagesRepository.findById(
                                    new UserVoucherId(order.getUser(), voucher)
                            ).orElseThrow(() -> new DataNotFoundException("UserVoucherUsages not found"));
                            voucherUsagesRepository.delete(voucherUsages);
                        }
                    }
                    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
                    for(OrderDetail orderDetail : orderDetails) {
                        ProductDetail productDetail = orderDetail.getProductDetail();
                        productDetail.setQuantity(productDetail.getQuantity() + orderDetail.getQuantity());
                        productDetailRepository.save(productDetail);
                        Product product = productDetail.getProduct();
                        product.setBuyQuantity(product.getBuyQuantity() - orderDetail.getQuantity());
                        product.setTotalQuantity(product.getTotalQuantity() + orderDetail.getQuantity());
                        productDetailRepository.save(productDetail);
                    }

                    break;
                default: break;
            }
            return order;
        }
        return null;
    }

    private void handleNotification(Order order, String text) {
        Notification notification = new Notification();
        notification.setContent(text);
        notification.setScope(Scope.FOR_USER);
        notification.setNotificationDate(LocalDateTime.now());
        notification.setRedirectTo("/orders/" + order.getId());
        notificationRepository.save(notification);
        UserNotification userNotification = new UserNotification();
        userNotification.setUser(order.getUser());
        userNotification.setNotification(notification);
        userNotification.setSeen(false);
        userNotificationRepository.save(userNotification);
        MessageResponse<Notification> messageResponse = new MessageResponse<>();
        messageResponse.setData(notification);
        messageResponse.setType("notification");
        messagingTemplate.convertAndSendToUser(order.getUser().getEmail(),
                "/queue/notifications", messageResponse);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    private double addVoucherDeliveryToOrder(double originalAmount, Voucher voucher) {
        if(voucher.getExpiredDate().isAfter(LocalDateTime.now()) &&
                originalAmount >= voucher.getMinAmount()) {
            double discountPrice = originalAmount * voucher.getDiscount();
            if(discountPrice >= voucher.getMaxPrice()) {
                discountPrice = voucher.getMaxPrice();
            }
            return discountPrice;

        }
        return 0;
    }


    private double handleAmount(List<ProductOrderDto> productOrders,
                                Order order, double originalAmount
    ) throws DataNotFoundException, OutOfInStockException {
        for (ProductOrderDto productOrder : productOrders) {
            ProductDetail productDetail = productDetailRepository.findById(productOrder.getProductDetailId())
                    .orElseThrow(() -> new DataNotFoundException("Product not found"));
            Product product = productDetail.getProduct();
            int quantity = productDetail.getQuantity() - productOrder.getQuantity();
            if(quantity < 0) {
                throw new OutOfInStockException("product out of in stock");
            }
            productDetail.setQuantity(quantity);
            product.setTotalQuantity(product.getTotalQuantity() - productOrder.getQuantity());
            int buyQuantity = product.getBuyQuantity() != null ? product.getBuyQuantity() : 0;
            product.setBuyQuantity(buyQuantity + productOrder.getQuantity());
            productDetailRepository.save(productDetail);
            productRepository.save(product);
            List<ProductPrice> productPrices = productPriceRepository
                    .findAllByProductId(product.getId());
            double price = product.getPrice();
            double discountedPrice = 0;
            if(!productPrices.isEmpty()){
                for (ProductPrice productPrice : productPrices) {
                    if(productPrice.getExpiredDate().isAfter(LocalDateTime.now())) {
                        if(productPrice.getDiscountedPrice() > discountedPrice) {
                            discountedPrice = productPrice.getDiscountedPrice();
                        }
                    }
                }
            }
            price = price - discountedPrice;
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(productOrder.getQuantity());
            orderDetail.setAmount(price * orderDetail.getQuantity());
            orderDetail.setOrder(order);
            orderDetail.setProductDetail(productDetail);
            orderDetailRepository.save(orderDetail);
            originalAmount += orderDetail.getAmount();
        }
        return originalAmount;
    }


    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setNotificationRepository(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Autowired
    public void setUserNotificationRepository(UserNotificationRepository userNotificationRepository) {
        this.userNotificationRepository = userNotificationRepository;
    }

    @Autowired
    public void setOrderVoucherRepository(OrderVoucherRepository orderVoucherRepository) {
        this.orderVoucherRepository = orderVoucherRepository;
    }
}
