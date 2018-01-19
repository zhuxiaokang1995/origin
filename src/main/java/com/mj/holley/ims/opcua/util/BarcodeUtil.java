package com.mj.holley.ims.opcua.util;

/**
 * @author zy Date: 16-11-25.
 */
public class BarcodeUtil {

    /**
     * 专用号可能是9,10或者11位，最后一位不会是0,所以从条码中取前11位，然后将最后的若干个0去除，剩余的则是真正的专用号
     *
     * @param product_no
     * @return
     */
    public static String getRealProduceNo(String product_no) {
        String productNO = product_no;
        if (productNO.lastIndexOf("0") == -1) {
            return productNO;
        }
        while (true) {
            // 最短的专用号是9位
            if (productNO.substring(0, productNO.lastIndexOf("0")).length() < 9) {
                return productNO;
            } else {
                productNO = productNO.substring(0, productNO.lastIndexOf("0"));
            }
        }
    }

    public static Boolean assertBarcodeValid(String barcode) {
        for (int i = 0; i < barcode.length(); i++) {
            if (Character.getNumericValue(barcode.charAt(i)) == -1) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    public static String fixProductNo(String product_no) {
        String productNO = product_no;
        int index = productNO.length();
        switch (index) {
            case 9:
                productNO += "005";
                break;
            case 10:
                productNO += "05";
                break;
            case 11:
                productNO += "5";
                break;
            default:
                break;
        }
        return productNO;
    }
}
