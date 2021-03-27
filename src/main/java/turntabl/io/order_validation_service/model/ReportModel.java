package turntabl.io.order_validation_service.model;

import lombok.Data;
import turntabl.io.order_validation_service.model.order.Order;

@Data
public class ReportModel {
    public Order validatedOrder;
    private String reportTitle;

    public Order getValidatedOrder() {
        return validatedOrder;
    }

    public void setValidatedOrder(Order validatedOrder) {
        this.validatedOrder = validatedOrder;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }
}
