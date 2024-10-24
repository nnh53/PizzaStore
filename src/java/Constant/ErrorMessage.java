package Constant;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hoangnn
 */
public enum ErrorMessage {
    //
    ORDER_DETAIL_NOT_EXISTS("Order detail not existed"),
    ORDER_NOT_EXISTS("Order not existed"),
    ORDER_ALREADY_EXISTS("Order already existed"),
    //product
    PRODUCT_ALREADY_EXISTS("Product already existed"),
    PRODUCT_NOT_EXISTS("Product not existed"),
    //cate
    CATEGORY_ALREADY_EXISTS("Category already existed"),
    CATEGORY_NOT_EXISTS("Category not existed"),
    //supplier
    SUPPLIER_ALREADY_EXISTS("Supplier already existed"),
    SUPPLIER_NOT_EXISTS("Supplier not existed"),
    //account
    ACCOUNT_NOT_EXISTS("Account not existed"),
    ACCOUNT_ALREADY_EXISTS("Account already existed"),
    ACCOUNT_OR_PASSWORD_INCORRECT("ACCOUNT or Password is incorrect"),
    //user
    USERNAME_ALREADY_EXISTS("UserName already existed"),
    USERNAME_NOT_EXISTS("UserName not existed"),
    USERNAME_OR_PASSWORD_INCORRECT("UserName or Password is incorrect"),
    INPUT_INVALID("Input not valid"),
    SOMETHING_WRONG("Something wrong happened, sorry for inconvenience");

    private final String message;

    //PHẢI CÓ CONSTRUCTOR để tạo obj MỚI CÓ THỂ gọi đc hàm enumToString
    ErrorMessage(String message) {
        this.message = message;
    }

    public String enumToString() {
        return message;
    }
}
