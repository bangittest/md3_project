package ra.view.home;

import ra.config.Validate;

import static ra.config.Color.RESET;

public class Cart {
    public void menuCart() {
                do {
                    System.out.println("\u001B[35m╔══════════════════════════════     CART     ════════════════════════════════╗");
                    System.out.println("\u001B[35m║                       \u001B[36m1. Hiển thị sản phẩm trong cart                      \u001B[35m║");
                    System.out.println("\u001B[35m║                       \u001B[36m2. Tăng số lượng                                     \u001B[35m║");
                    System.out.println("\u001B[35m║                       \u001B[36m3. Giảm số lượng                                     \u001B[35m║");
                    System.out.println("\u001B[35m║                       \u001B[36m4. Xóa sản phẩm                                      \u001B[35m║");
                    System.out.println("\u001B[35m║                       \u001B[36m4. Thanh toán                                        \u001B[35m║");
                    System.out.println("\u001B[35m║                       \u001B[31m0. Quay lại                                          \u001B[35m║");
                    System.out.println("\u001B[35m╚════════════════════════════════════════════════════════════════════════════╝" + RESET);
                    System.out.print("Mời lựa chọn (0/1/2/3/4): ");
                    switch (Validate.validateInt()) {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 0:
                            return;
                        default:
                            System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                            break;
                    }
                } while (true);
            }

}
