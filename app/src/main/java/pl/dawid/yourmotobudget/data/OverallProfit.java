package pl.dawid.yourmotobudget.data;

public class OverallProfit {

    private Double costsTextView = 0.0;
    private Double salaryTextView = 0.0;
    private Double bonusTextView = 0.0;
    private Double supplementTextView = 0.0;
    private Double priceItem = 0.0;
    private Double priceHour = 0.0;

    public OverallProfit(){}

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
}
