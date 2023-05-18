// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import primer.po.Items;
import primer.po.PurchaseOrderType;
import primer.po.USAddress;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
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