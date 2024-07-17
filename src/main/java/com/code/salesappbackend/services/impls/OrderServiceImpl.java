package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.dtos.requests.OrderDto;
import com.code.salesappbackend.dtos.requests.ProductOrderDto;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.exceptions.OutOfInStockException;
import com.code.salesappbackend.mappers.AddressMapper;
import com.code.salesappbackend.models.*;
import com.code.salesappbackend.models.enums.DeliveryMethod;
import com.code.salesappbackend.models.enums.OrderStatus;
import com.code.salesappbackend.models.enums.Scope;
import com.code.salesappbackend.models.enums.VoucherType;
import com.code.salesappbackend.models.id_classes.UserVoucherId;
import com.code.salesappbackend.repositories.*;
import com.code.salesappbackend.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .user(user)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
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
                            order.setDeliveryAmount(order.getDeliveryAmount() - discountPrice);
                        } else {
                            order.setDiscountedPrice(
                                    (order.getDiscountedPrice() == null ? 0 : order.getDiscountedPrice())
                                            + discountPrice);

                        }
                        userVoucherRepository.save(userVoucher);
                    }
                } else {
                    Optional<VoucherUsages> voucherUsages = voucherUsagesRepository.findById(userVoucherId);
                    double discountPrice = addVoucherDeliveryToOrder(originalAmount, voucher);
                    if(voucherUsages.isEmpty()) {
                        if(voucher.getVoucherType().equals(VoucherType.FOR_DELIVERY)) {
                            order.setDeliveryAmount(order.getDeliveryAmount() - discountPrice);
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



}
