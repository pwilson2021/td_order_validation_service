package turntabl.io.order_validation_service.model;

public class OrderResponse {
//    add the xml annotations on top of the class
    private String isOrderValid;
    private String msg;

    public String getIsOrderValid() {
        return isOrderValid;
    }

    public void setIsOrderValid(String isOrderValid) {
        this.isOrderValid = isOrderValid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
