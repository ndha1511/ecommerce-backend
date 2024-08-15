ALTER TABLE orders
    DROP CONSTRAINT IF EXISTS orders_order_status_check;
ALTER TABLE orders
    ADD CONSTRAINT orders_order_status_check
        CHECK (order_status::text = ANY (ARRAY['PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'PAID', 'UNPAID']::text[]));
ALTER TABLE notifications
ADD COLUMN redirect_to TEXT;