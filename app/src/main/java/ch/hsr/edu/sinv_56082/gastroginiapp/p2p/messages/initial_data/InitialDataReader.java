package ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages.initial_data;

import android.util.Log;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.serialization.ModelHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages.MessageObject;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.client.P2pClient;

public class InitialDataReader extends MessageObject<InitialDataMessage> {
    private P2pClient p2pClient;

    public InitialDataReader(P2pClient p2pClient) {
        super(InitialDataMessage.class);
        this.p2pClient = p2pClient;
    }

    @Override
    public void handleMessage(final InitialDataMessage initData, String fromAddress) {
        if (p2pClient.isInitialized()) return;

        Log.d("initDataReader", "handleMessage: "+initData.event);

        final Person updatedHost = updateHost(initData);
        final ProductList updatedList = updateProductList(initData);
        final Event updatedEvent = updateEvent(initData, updatedHost, updatedList);
        updateTables(initData, updatedEvent);
        updateProducts(initData, updatedList);

        p2pClient.onInitDataSuccess.consume(updatedEvent.getUuid().toString());
        p2pClient.setInitialized(true);
    }

    private void updateProducts(InitialDataMessage initData, final ProductList updatedList) {
        for (final Product p: initData.products){
            final ProductCategory cat = updateProductCategory(p);
            final ProductDescription desc = updateProductDescription(p, cat);
            updateProduct(updatedList, p, desc);
        }
    }

    private void updateProduct(final ProductList updatedList, final Product p, final ProductDescription desc) {
        new ModelHolder<>(Product.class).setModel(p).updateOrSave(new Consumer<Product>() {
            @Override
            public void consume(Product product) {
                product.price = p.price;
                product.volume = p.volume;
                product.productDescription = desc;
                product.productList = updatedList;
            }
        });
    }

    private ProductDescription updateProductDescription(final Product p, final ProductCategory cat) {
        return new ModelHolder<>(ProductDescription.class).setModel(p.productDescription).updateOrSave(new Consumer<ProductDescription>() {
            @Override
            public void consume(ProductDescription description) {
                description.name = p.productDescription.name;
                description.description = p.productDescription.description;
                description.productCategory = cat;
            }
        }).getModel();
    }

    private ProductCategory updateProductCategory(final Product p) {
        return new ModelHolder<>(ProductCategory.class).setModel(p.productDescription.productCategory).updateOrSave(new Consumer<ProductCategory>() {
            @Override
            public void consume(ProductCategory productCategory) {
                productCategory.name = p.productDescription.productCategory.name;
            }
        }).getModel();
    }

    private void updateTables(InitialDataMessage initData, final Event updatedEvent) {
        for (final EventTable table: initData.tables){
            new ModelHolder<>(EventTable.class).setModel(table).updateOrSave(new Consumer<EventTable>() {
                @Override
                public void consume(EventTable eventTable) {
                    eventTable.name = table.name;
                    eventTable.number = table.number;
                    eventTable.event = updatedEvent;
                }
            });
        }
    }

    private Event updateEvent(final InitialDataMessage initData, final Person updatedHost, final ProductList updatedList) {
        return new ModelHolder<>(Event.class).setModel(initData.event).updateOrSave(new Consumer<Event>() {
            @Override
            public void consume(Event event) {
                event.productList = updatedList;
                event.host = updatedHost;
                event.startTime = initData.event.startTime;
                event.endTime = initData.event.endTime;
            }
        }).getModel();
    }

    private ProductList updateProductList(final InitialDataMessage initData) {
        return new ModelHolder<>(ProductList.class).setModel(initData.event.productList).updateOrSave(new Consumer<ProductList>() {
            @Override
            public void consume(ProductList productList) {
                productList.name = initData.event.productList.name;
            }
        }).getModel();
    }

    private Person updateHost(final InitialDataMessage initData) {
        return new ModelHolder<>(Person.class).setModel(initData.event.host).updateOrSave(new Consumer<Person>() {
            @Override
            public void consume(Person person) {
                person.firstName = initData.event.host.firstName;
                person.lastName = initData.event.host.lastName;
            }
        }).getModel();
    }
}
