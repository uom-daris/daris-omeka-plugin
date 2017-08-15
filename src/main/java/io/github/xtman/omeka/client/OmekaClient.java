package io.github.xtman.omeka.client;

import io.github.xtman.http.client.HttpClient;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.client.command.collection.GetCollectionsCommand;
import io.github.xtman.omeka.client.command.element.GetElementsCommand;
import io.github.xtman.omeka.client.command.elementset.GetElementSetsCommand;
import io.github.xtman.omeka.client.command.file.GetFilesCommand;
import io.github.xtman.omeka.client.command.item.GetItemsCommand;
import io.github.xtman.omeka.client.command.itemtype.GetItemTypesCommand;
import io.github.xtman.omeka.client.command.tag.GetTagsCommand;
import io.github.xtman.omeka.model.Collection;
import io.github.xtman.omeka.model.Element;
import io.github.xtman.omeka.model.ElementSet;
import io.github.xtman.omeka.model.File;
import io.github.xtman.omeka.model.Item;
import io.github.xtman.omeka.model.ItemType;
import io.github.xtman.omeka.model.Resources;
import io.github.xtman.omeka.model.Site;
import io.github.xtman.omeka.model.Tag;
import io.github.xtman.omeka.model.User;
import io.github.xtman.omeka.model.builder.CollectionBuilder;
import io.github.xtman.omeka.model.builder.ElementBuilder;
import io.github.xtman.omeka.model.builder.ElementSetBuilder;
import io.github.xtman.omeka.model.builder.FileBuilder;
import io.github.xtman.omeka.model.builder.ItemBuilder;
import io.github.xtman.omeka.model.builder.ItemTypeBuilder;

public interface OmekaClient extends HttpClient {

    String endPoint();

    String apiKey();

    /*
     * collection
     */

    Collection createCollection(CollectionBuilder c) throws Throwable;

    Collection getCollection(long id) throws Throwable;

    Collection updateCollection(long id, CollectionBuilder c) throws Throwable;

    void deleteCollection(long id) throws Throwable;

    ResultSet<Collection> listCollections(GetCollectionsCommand.Params params) throws Throwable;

    long countCollections() throws Throwable;

    /*
     * element_set
     */

    ElementSet createElementSet(ElementSetBuilder elementSet) throws Throwable;

    ElementSet getElementSet(long id) throws Throwable;

    ElementSet updateElementSet(long id, ElementSetBuilder elementSet) throws Throwable;

    void deleteElementSet(long id) throws Throwable;

    ResultSet<ElementSet> listElementSets(GetElementSetsCommand.Params params) throws Throwable;

    long countElementSets() throws Throwable;

    /*
     * element
     */

    Element createElement(ElementBuilder element) throws Throwable;

    Element getElement(long id) throws Throwable;

    Element updateElement(long id, ElementBuilder element) throws Throwable;

    void deleteElement(long id) throws Throwable;

    ResultSet<Element> listElements(GetElementsCommand.Params params) throws Throwable;

    long countElements() throws Throwable;

    /*
     * item type
     */
    ItemType createItemType(ItemTypeBuilder itemType) throws Throwable;

    ItemType getItemType(long id) throws Throwable;

    ItemType updateItemType(long id, ItemTypeBuilder itemType) throws Throwable;

    void deleteItemType(long id) throws Throwable;

    ResultSet<ItemType> listItemTypes(GetItemTypesCommand.Params params) throws Throwable;

    long countItemTypes() throws Throwable;

    /*
     * item
     */
    Item createItem(ItemBuilder item) throws Throwable;

    Item getItem(long id) throws Throwable;

    Item updateItem(long id, ItemBuilder item) throws Throwable;

    void deleteItem(long id) throws Throwable;

    ResultSet<Item> listItems(GetItemsCommand.Params params) throws Throwable;

    long countItems() throws Throwable;

    /*
     * file
     */
    File createFile(FileBuilder file) throws Throwable;

    File getFile(long id) throws Throwable;

    File updateFile(long id, FileBuilder file) throws Throwable;

    void deleteFile(long id) throws Throwable;

    ResultSet<File> listFiles(GetFilesCommand.Params params) throws Throwable;

    long countFiles() throws Throwable;

    /*
     * tag
     */
    Tag getTag(long id) throws Throwable;

    void deleteTag(long id) throws Throwable;

    ResultSet<Tag> listTags(GetTagsCommand.Params params) throws Throwable;

    long countTags() throws Throwable;

    /*
     * resource
     */
    Resources getResources() throws Throwable;

    /*
     * site
     */
    Site getSite() throws Throwable;

    /*
     * user
     */
    User getUser(long id) throws Throwable;

}
