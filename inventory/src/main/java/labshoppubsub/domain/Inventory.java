package labshoppubsub.domain;

import labshoppubsub.InventoryApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Inventory_table")
@Data

public class Inventory  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private Long stock;
    
    
    
    
    
    private Long orderId;

    @PostPersist
    public void onPostPersist(){
    }

    public static InventoryRepository repository(){
        InventoryRepository inventoryRepository = InventoryApplication.applicationContext.getBean(InventoryRepository.class);
        return inventoryRepository;
    }

<<<<<<< HEAD
    public static void decreaseStock(OrderPlaced orderPlaced) {
=======



    public static void decreaseStock(OrderPlaced orderPlaced){

        /** Example 1:  new item 
        Inventory inventory = new Inventory();
        repository().save(inventory);
>>>>>>> origin/template

        /** fill out following code  */
        
        repository().findByOrderId(orderPlaced.getId()).ifPresent(inventory->{
            
            inventory.setStock(inventory.getStock() - orderPlaced.getQty()); // do something
            repository().save(inventory);


         });
      

        
    }


}
