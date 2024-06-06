package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.dtos.requests.OrderDto;
import com.code.salesappbackend.dtos.requests.ProductOrderDto;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.mappers.AddressMapper;
import com.code.salesappbackend.models.*;
import com.code.salesappbackend.models.enums.DeliveryMethod;
import com.code.salesappbackend.models.enums.OrderStatus;
import com.code.salesappbackend.repositories.*;
import com.code.salesappbackend.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

    public OrderServiceImpl(JpaRepository<Order, String> repository) {
        super(repository);
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


    @Override
    @Transactional(rollbackFor = {DataNotFoundException.class})
    public Order save(OrderDto orderDto) throws DataNotFoundException {
        List<ProductOrderDto> productOrders = orderDto.getProductOrders();
        User user = userRepository.findById(orderDto.getUserId())
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
        for (ProductOrderDto productOrder : productOrders) {
            ProductDetail productDetail = productDetailRepository.findById(productOrder.getProductDetailId())
                    .orElseThrow(() -> new DataNotFoundException("Product not found"));
            Product product = productDetail.getProduct();
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
            orderDetail.setQuantity(productDetail.getQuantity());
            orderDetail.setAmount(price * orderDetail.getQuantity());
            orderDetail.setOrder(order);
            orderDetail.setProductDetail(productDetail);
            orderDetailRepository.save(orderDetail);
            originalAmount += orderDetail.getAmount();
        }

//        List<Long> vouchers = orderDto.getVouchers();
//        for (Long voucherId : vouchers) {
//            Voucher voucher = voucherRepository.findById(voucherId)
//                    .orElseThrow(() -> new DataNotFoundException("Voucher not found"));
//
//
//        }
        order.setOriginalAmount(originalAmount);
        return super.save(order);
    }


}
