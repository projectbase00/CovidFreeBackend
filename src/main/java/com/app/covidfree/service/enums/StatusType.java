package com.app.covidfree.service.enums;

public enum StatusType {

        REJECTED(0),
        ACCEPTED(1), 
        PENDING_SMS_VERIFICATION(2),
        PENDING_ADMIN_VERIFICATION(3); 
    
        public final Integer status;
    
        private StatusType(Integer status){
            this.status = status;
        }
    
        public Integer getStatus(){
            return status;
        }
    
}
