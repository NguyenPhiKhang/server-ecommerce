package com.khangse616.serverecommerce.mapper;

import com.khangse616.serverecommerce.dto.CommentDTO;
import com.khangse616.serverecommerce.models.Comment;
import com.khangse616.serverecommerce.models.User;
import com.khangse616.serverecommerce.utils.ImageUtil;

public class CommentDTOMapper implements RowMapper<CommentDTO, Comment> {
    @Override
    public CommentDTO mapRow(Comment comment) {
        try{
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setParentId(comment.getParentId());
            User user = comment.getUser();
            commentDTO.setCustomerId(user.getId());
            commentDTO.setCustomerName(user.getName());
//            commentDTO.setCustomerLogo( ImageUtil.addressImage(user.getImageAvatar().getId()));
            commentDTO.setCustomerLogo(user.getImageAvatar().getLink());
            commentDTO.setData(comment.getData());
            commentDTO.setShop(user.isShop());
            commentDTO.setTimeCreated(comment.getTimeCreated());
            commentDTO.setTimeUpdated(comment.getTimeUpdated());

            return commentDTO;
        }catch (Exception ex){
            return null;
        }
    }
}
