package labshoppubsub.external;

import org.springframework.stereotype.Service;

@Service
public class InventoryServiceFallback implements InventoryService  {
    public Inventory getInventory(Long id){
        Inventory fallbackValue = new Inventory();        

        System.out.println("#### Inventory Fallback process executed. ####");
        // 상품서비스 장애에 따른 가상의 재고를 설정한 다음, 
        // 서비스가 정상화 되면, 
        // 이를 실제 상품 재고에 반영(차감)한다.
        fallbackValue.setStock(100L);        
        return fallbackValue;
    }
}