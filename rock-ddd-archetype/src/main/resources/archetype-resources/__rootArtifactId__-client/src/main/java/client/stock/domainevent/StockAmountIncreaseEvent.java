#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client.stock.domainevent;

import lombok.Data;
import com.fzd.rock.client.event.DomainEvent;

@Data
public class StockAmountIncreaseEvent extends DomainEvent<Void>{

}
