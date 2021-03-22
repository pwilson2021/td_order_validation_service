package turntabl.io.order_validation_service.model;

import lombok.Data;

@Data
public class ReportModel {
    public OrderModel validatedOrder;
    private String reportTitle;

    public OrderModel getValidatedOrder() {
        return validatedOrder;
    }

    public void setValidatedOrder(OrderModel validatedOrder) {
        this.validatedOrder = validatedOrder;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }
}
