// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import primer.po.Items;
import primer.po.PurchaseOrderType;
import primer.po.USAddress;

import javax.xml.bind.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // create a JAXBContext capable of handling classes generated into
            // the primer.po package
            JAXBContext jc = JAXBContext.newInstance( "primer.po" );

            // create an Unmarshaller
            Unmarshaller u = jc.createUnmarshaller();

            // unmarshal a po instance document into a tree of Java content
            // objects composed of classes from the primer.po package.
            JAXBElement<?> poElement =
                    (JAXBElement<?>)u.unmarshal(Files.newInputStream(Paths.get("po.xml")));
            PurchaseOrderType po = (PurchaseOrderType)poElement.getValue();


            // examine some of the content in the PurchaseOrder
            System.out.println( "Ship the following items to: " );

            // display the shipping address
            USAddress address = po.getShipTo();
            displayAddress( address );

            // display the items
            Items items = po.getItems();
            displayItems( items );

System.out.println("modify the billto address and write back...");
            //demo marshal to output modify result
            // change the billto address
            USAddress billAddress = po.getBillTo();
            billAddress.setName( "John Bob" );
            billAddress.setStreet( "242 Main Street" );
            billAddress.setCity( "Beverly Hills" );
            billAddress.setState( "CA" );
            billAddress.setZip( new BigDecimal( "90210" ) );

            // create a Marshaller and marshal to a file
            Marshaller m = jc.createMarshaller();
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            m.marshal(poElement, Files.newOutputStream(Paths.get("pobill2.xml")));
            m.marshal( poElement, System.out );

            //add one more item
            Items.Item newItem = new primer.po.ObjectFactory().createItemsItem();
            newItem.setProductName("Porche");
            newItem.setPartNum("242-PO");
            newItem.setQuantity(1);
            newItem.setUSPrice(new BigDecimal("1120.09"));
//            newItem.setShipDate(DatatypeFactory.newInstance().newXMLGregorianCalendar("2023-05-18T19:56:00+8:00"));
            items.getItem().add(newItem);

            System.out.println("add an new item...");

            m.marshal(poElement, Files.newOutputStream(Paths.get("pobill2.xml")));
            m.marshal( poElement, System.out );





        } catch(JAXBException | IOException je ) {
            je.printStackTrace();
        }
    }

    public static void displayAddress( USAddress address ) {
        // display the address
        System.out.println( "\t" + address.getName() );
        System.out.println( "\t" + address.getStreet() );
        System.out.println( "\t" + address.getCity() +
                ", " + address.getState() +
                " "  + address.getZip() );
        System.out.println( "\t" + address.getCountry() + "\n");
    }

    public static void displayItems( Items items ) {
        // the items object contains a List of primer.po.ItemType objects
        List<Items.Item> itemTypeList = items.getItem();


        // iterate over List
        for (Items.Item item : itemTypeList) {
            System.out.println("\t" + item.getQuantity() +
                    " copies of \"" + item.getProductName() +
                    "\"");
        }
    }
}