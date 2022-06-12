package org.iesmurgi.proyectolevidaviddam.Enviroment;

public enum CONSTANT {

    URL("http://tux.iesmurgi.org:11230"),
    SOCKET("http://tux.iesmurgi.org:11236");

    private String url;

    CONSTANT(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
