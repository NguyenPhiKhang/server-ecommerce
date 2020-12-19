package com.khangse616.serverecommerce.mapper;

import com.khangse616.serverecommerce.dto.ShopDTO;
import com.khangse616.serverecommerce.models.Shop;
import com.khangse616.serverecommerce.utils.ImageUtil;

public class ShopDTOMapper implements RowMapper<ShopDTO, Shop> {
    @Override
    public ShopDTO mapRow(Shop shop) {
        try{
            ShopDTO shopDTO = new ShopDTO();
            shopDTO.setId(shop.getId());
            shopDTO.setCustomerIdOfShop(shop.getUser().getId());
            shopDTO.setShopName(shop.getName());
            shopDTO.setShopLogo( ImageUtil.addressImage(shop.getLogo().getId()));
            shopDTO.setGoodReviewPercent(shop.getGoodReviewPercent());
            shopDTO.setScore(shop.getScore());
            shopDTO.setPhoneNumber(shop.getPhoneNumber());
            shopDTO.setWarehouseCity(shop.getWarehouseCity());
            shopDTO.setCertified(shop.isCertified());

            return shopDTO;
        }catch (Exception ex){
            return null;
        }
    }
}
