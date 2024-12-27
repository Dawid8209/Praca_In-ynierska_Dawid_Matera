package pl.dawid.yourmotobudget.data;

public class OverallProfit {

    private Double costsTextView;
    private Double salaryTextView;
    private Double bonusTextView;
    private Double supplementTextView;
    private Double priceItem;
    private Double priceHour;
    private Double expensesTextView;
    private Double profitTextView;
    private Double togetherTextView;

    public OverallProfit(Double costsTextView,
                         Double salaryTextView,
                         Double bonusTextView,
                         Double supplementTextView,
                         Double priceItem,
                         Double priceHour,
                         Double expensesTextView,
                         Double profitTextView,
                         Double togetherTextView) {
        this.costsTextView = costsTextView;
        this.salaryTextView = salaryTextView;
        this.bonusTextView = bonusTextView;
        this.supplementTextView = supplementTextView;
        this.priceItem = priceItem;
        this.priceHour = priceHour;
        this.expensesTextView = expensesTextView;
        this.profitTextView = profitTextView;
        this.togetherTextView = togetherTextView;
    }

    public Double getCostsTextView() { return costsTextView; }
    public void setCostsTextView(Double costsTextView) { this.costsTextView = costsTextView; }

    public Double getSalaryTextView() { return salaryTextView; }
    public void setSalaryTextView(Double salaryTextView) { this.salaryTextView = salaryTextView; }

    public Double getBonusTextView() { return bonusTextView; }
    public void setBonusTextView(Double bonusTextView) { this.bonusTextView = bonusTextView; }

    public Double getSupplementTextView() { return supplementTextView; }
    public void setSupplementTextView(Double supplementTextView) { this.supplementTextView = supplementTextView; }

    public Double getPriceItem() { return priceItem; }
    public void setPriceItem(Double priceItem) { this.priceItem = priceItem; }

    public Double getPriceHour() { return priceHour; }
    public void setPriceHour(Double priceHour) { this.priceHour = priceHour; }

    public Double getExpensesTextView() { return expensesTextView; }
    public void setExpensesTextView(Double expensesTextView) { this.expensesTextView = expensesTextView; }

    public Double getProfitTextView() { return profitTextView; }
    public void setProfitTextView(Double profitTextView) { this.profitTextView = profitTextView; }

    public Double getTogetherTextView() { return togetherTextView; }
    public void setTogetherTextView(Double togetherTextView) { this.togetherTextView = togetherTextView; }
}
