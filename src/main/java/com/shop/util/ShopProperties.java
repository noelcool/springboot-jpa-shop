package com.shop.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShopProperties {

    static String imageUrl;
    static String imageUploadPath;

    public static String getImageUrl() {
        return ShopProperties.imageUrl;
    }

    public static String getImageUploadPath() {
        return ShopProperties.imageUploadPath;
    }

    @Value("${shop.image.url}")
    public void setImageUrl(String imageUrl) {
        ShopProperties.imageUrl = imageUrl;
    }

    @Value("${shop.image.upload-path}")
    public void setImageUploadPath(String imageUploadPath) {
        ShopProperties.imageUploadPath = imageUploadPath;
    }



}
