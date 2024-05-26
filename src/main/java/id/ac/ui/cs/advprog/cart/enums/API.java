package id.ac.ui.cs.advprog.cart.enums;

public enum API {
    ALL_PRODUCT("https://toytopia-product-feiujl7vfq-uc.a.run.app/api/product-service/all-products"),
    PRODUCT("https://toytopia-product-feiujl7vfq-uc.a.run.app/api/product-service/product/id"),
    VOUCHER_BY_ID("http://35.240.180.63/voucher/code");
    private final String url;
    API(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
