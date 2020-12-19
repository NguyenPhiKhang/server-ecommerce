package com.khangse616.serverecommerce.mapper;

import com.khangse616.serverecommerce.dto.ProductItemDTO;
import com.khangse616.serverecommerce.models.Product;
import com.khangse616.serverecommerce.models.RatingStar;
import com.khangse616.serverecommerce.models.Shop;
import com.khangse616.serverecommerce.utils.ImageUtil;

import java.text.DecimalFormat;

public class ProductItemDTOMapper implements RowMapper<ProductItemDTO, Product> {
    @Override
    public ProductItemDTO mapRow(Product product) {
        try {
            ProductItemDTO productItemDTO = new ProductItemDTO();
            productItemDTO.setId(product.getId());
            productItemDTO.setName(product.getName());
            Shop shop = product.getShop();
            productItemDTO.setAdminId(shop.getId());
            productItemDTO.setEvent(product.isEvent());
            productItemDTO.setCategory(product.getCategory());
            productItemDTO.setFreeShip(product.isFreeShip());
            productItemDTO.setPromotion(product.isPromotion());
            productItemDTO.setPrice(product.getPrice());
            productItemDTO.setPromotionPercent(product.getPromotionPercent());
            productItemDTO.setFinalPrice(product.getFinalPrice());
            productItemDTO.setOrderCount(product.getOrderCount());
            productItemDTO.setImgUrl(ImageUtil.addressImage(product.getImgUrl().getId()));
            productItemDTO.setShopId(shop.getId());
            productItemDTO.setShopName(shop.getName());
            productItemDTO.setShopWarehouseCity(shop.getWarehouseCity());
            productItemDTO.setCountRating(product.getRatings().size());
            RatingStar ratingStar = product.getRatingStar();
            int totalStar = ratingStar.getStar1() + ratingStar.getStar2() + ratingStar.getStar3() + ratingStar.getStar4() + ratingStar.getStar5();
            float percentStar = totalStar > 0 ? (float) (ratingStar.getStar1() + ratingStar.getStar2() * 2 + ratingStar.getStar3() * 3 + ratingStar.getStar4() * 4 + ratingStar.getStar5() * 5)
                    / totalStar : 0;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            productItemDTO.setPercentStar(Float.parseFloat(decimalFormat.format(percentStar)));
            return productItemDTO;
        } catch (Exception ex) {
            return null;
        }
    }
}
