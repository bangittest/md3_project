package ra.config;

import java.text.DecimalFormat;

public class Validate {
    //validate nhập số
    public static int validateInt(){
        int n ;
        System.out.println("Mời nhập: ");
        while (true){
            try {
                n = Integer.parseInt(Config.scanner().nextLine());
                break;
            }catch (NumberFormatException e){
                System.out.println("Sai định dạng mời nhập lại");
            }
        }
        return n;
    }

    //format tiền tệ
    public static DecimalFormat formatVnd(){
        return new DecimalFormat("###,###,###");
    }


    // Validate email
    public static String validateEmail(){
        String email;
        while (true) {
//            System.out.println("Mời nhập email: ");
            email = Config.scanner().nextLine();
            if (email.matches("[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*@[a-z]+(\\.[a-z]+){1,2}")) {
                break;
            } else {
                System.out.println("Email không đúng định dạng mời nhập lại: ");
            }
        }
        return email;
    }
    // Validate phone number
    public static String validatePhone(){
        String phone;
        while (true) {
//            System.out.println("Mời nhập số điện thoại: ");
            phone = Config.scanner().nextLine();
            if (phone.matches("^(0|84)\\d{9,10}$")) {
                break;
            } else {
                System.out.println("Số điện thoại không đúng định dạng mời nhập lại: ");
            }
        }
        return phone;
    }

    //validate String
    public static String validateString(){
        String s;
        while (true){
            s=Config.scanner().nextLine();
            if (s.isEmpty()){
                System.out.println("Không được để trống mời nhập lại");
            }else {
                break;
            }
        }
        return s;
    }
}
