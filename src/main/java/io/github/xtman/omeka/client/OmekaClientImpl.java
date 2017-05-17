package io.github.xtman.omeka.client;

import java.io.InputStream;

import io.github.xtman.http.client.HttpClientBase;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.client.command.collection.DeleteCollectionCommand;
import io.github.xtman.omeka.client.command.collection.GetCollectionCommand;
import io.github.xtman.omeka.client.command.collection.GetCollectionsCommand;
import io.github.xtman.omeka.client.command.collection.PostCollectionCommand;
import io.github.xtman.omeka.client.command.collection.PutCollectionCommand;
import io.github.xtman.omeka.client.command.element.DeleteElementCommand;
import io.github.xtman.omeka.client.command.element.GetElementCommand;
import io.github.xtman.omeka.client.command.element.GetElementsCommand;
import io.github.xtman.omeka.client.command.element.PostElementCommand;
import io.github.xtman.omeka.client.command.element.PutElementCommand;
import io.github.xtman.omeka.client.command.elementset.DeleteElementSetCommand;
import io.github.xtman.omeka.client.command.elementset.GetElementSetCommand;
import io.github.xtman.omeka.client.command.elementset.GetElementSetsCommand;
import io.github.xtman.omeka.client.command.elementset.PostElementSetCommand;
import io.github.xtman.omeka.client.command.elementset.PutElementSetCommand;
import io.github.xtman.omeka.client.command.file.DeleteFileCommand;
import io.github.xtman.omeka.client.command.file.GetFileCommand;
import io.github.xtman.omeka.client.command.file.GetFilesCommand;
import io.github.xtman.omeka.client.command.file.PostFileCommand;
import io.github.xtman.omeka.client.command.file.PutFileCommand;
import io.github.xtman.omeka.client.command.item.DeleteItemCommand;
import io.github.xtman.omeka.client.command.item.GetItemCommand;
import io.github.xtman.omeka.client.command.item.GetItemsCommand;
import io.github.xtman.omeka.client.command.item.PostItemCommand;
import io.github.xtman.omeka.client.command.item.PutItemCommand;
import io.github.xtman.omeka.client.command.itemtype.DeleteItemTypeCommand;
import io.github.xtman.omeka.client.command.itemtype.GetItemTypeCommand;
import io.github.xtman.omeka.client.command.itemtype.GetItemTypesCommand;
import io.github.xtman.omeka.client.command.itemtype.PostItemTypeCommand;
import io.github.xtman.omeka.client.command.itemtype.PutItemTypeCommand;
import io.github.xtman.omeka.client.command.resource.GetResourcesCommand;
import io.github.xtman.omeka.client.command.site.GetSiteCommand;
import io.github.xtman.omeka.client.command.tag.DeleteTagCommand;
import io.github.xtman.omeka.client.command.tag.GetTagCommand;
import io.github.xtman.omeka.client.command.tag.GetTagsCommand;
import io.github.xtman.omeka.client.command.user.GetUserCommand;
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

public class OmekaClientImpl extends HttpClientBase implements OmekaClient {

    private String _endPoint;
    private String _apiKey;

    public OmekaClientImpl(String endPoint, String apiKey) {
        _endPoint = endPoint;
        _apiKey = apiKey;
    }

    @Override
    public String endPoint() {
        return _endPoint;
    }

    @Override
    public String apiKey() {
        return _apiKey;
    }

    @Override
    public Collection createCollection(CollectionBuilder c) throws Throwable {
        return new PostCollectionCommand(this, c).execute();
    }

    @Override
    public Collection updateCollection(long id, CollectionBuilder c) throws Throwable {
        return new PutCollectionCommand(this, id, c).execute();
    }

    @Override
    public void deleteCollection(long id) throws Throwable {
        new DeleteCollectionCommand(this, id).execute();
    }

    @Override
    public ResultSet<Collection> listCollections(GetCollectionsCommand.Params params) throws Throwable {
        return new GetCollectionsCommand(this, params).execute();
    }

    @Override
    public ResultSet<ElementSet> listElementSets(GetElementSetsCommand.Params params) throws Throwable {
        return new GetElementSetsCommand(this, params).execute();
    }

    @Override
    public ElementSet createElementSet(ElementSetBuilder elementSet) throws Throwable {
        return new PostElementSetCommand(this, elementSet).execute();
    }

    @Override
    public ElementSet updateElementSet(long id, ElementSetBuilder elementSet) throws Throwable {
        return new PutElementSetCommand(this, id, elementSet).execute();
    }

    @Override
    public void deleteElementSet(long id) throws Throwable {
        new DeleteElementSetCommand(this, id).execute();
    }

    @Override
    public Element createElement(ElementBuilder element) throws Throwable {
        return new PostElementCommand(this, element).execute();
    }

    @Override
    public Element updateElement(long id, ElementBuilder element) throws Throwable {
        return new PutElementCommand(this, id, element).execute();
    }

    @Override
    public void deleteElement(long id) throws Throwable {
        new DeleteElementCommand(this, id).execute();
    }

    @Override
    public ResultSet<Element> listElements(GetElementsCommand.Params params) throws Throwable {
        return new GetElementsCommand(this, params).execute();
    }

    @Override
    public Collection getCollection(long id) throws Throwable {
        return new GetCollectionCommand(this, id).execute();
    }

    @Override
    public ElementSet getElementSet(long id) throws Throwable {
        return new GetElementSetCommand(this, id).execute();
    }

    @Override
    public Element getElement(long id) throws Throwable {
        return new GetElementCommand(this, id).execute();
    }

    @Override
    public ItemType createItemType(ItemTypeBuilder itemType) throws Throwable {
        return new PostItemTypeCommand(this, itemType).execute();
    }

    @Override
    public ItemType getItemType(long id) throws Throwable {
        return new GetItemTypeCommand(this, id).execute();
    }

    @Override
    public ItemType updateItemType(long id, ItemTypeBuilder itemType) throws Throwable {
        return new PutItemTypeCommand(this, id, itemType).execute();
    }

    @Override
    public void deleteItemType(long id) throws Throwable {
        new DeleteItemTypeCommand(this, id).execute();
    }

    @Override
    public ResultSet<ItemType> listItemTypes(GetItemTypesCommand.Params params) throws Throwable {
        return new GetItemTypesCommand(this, params).execute();
    }

    @Override
    public Item createItem(ItemBuilder item) throws Throwable {
        return new PostItemCommand(this, item).execute();
    }

    @Override
    public Item getItem(long id) throws Throwable {
        return new GetItemCommand(this, id).execute();
    }

    @Override
    public Item updateItem(long id, ItemBuilder item) throws Throwable {
        return new PutItemCommand(this, id, item).execute();
    }

    @Override
    public void deleteItem(long id) throws Throwable {
        new DeleteItemCommand(this, id).execute();
    }

    @Override
    public ResultSet<Item> listItems(GetItemsCommand.Params params) throws Throwable {
        return new GetItemsCommand(this, params).execute();
    }

    @Override
    public File createFile(FileBuilder file, String fileName, InputStream in, long length, String mimeType)
            throws Throwable {
        return new PostFileCommand(this, file, fileName, in, length, mimeType).execute();
    }

    @Override
    public File getFile(long id) throws Throwable {
        return new GetFileCommand(this, id).execute();
    }

    @Override
    public File updateFile(long id, FileBuilder file) throws Throwable {
        return new PutFileCommand(this, id, file).execute();
    }

    @Override
    public void deleteFile(long id) throws Throwable {
        new DeleteFileCommand(this, id).execute();
    }

    @Override
    public ResultSet<File> listFiles(GetFilesCommand.Params params) throws Throwable {
        return new GetFilesCommand(this, params).execute();
    }

    @Override
    public Tag getTag(long id) throws Throwable {
        return new GetTagCommand(this, id).execute();
    }

    @Override
    public void deleteTag(long id) throws Throwable {
        new DeleteTagCommand(this, id).execute();
    }

    @Override
    public ResultSet<Tag> listTags(GetTagsCommand.Params params) throws Throwable {
        return new GetTagsCommand(this, params).execute();
    }

    @Override
    public Resources getResources() throws Throwable {
        return new GetResourcesCommand(this).execute();
    }

    @Override
    public Site getSite() throws Throwable {
        return new GetSiteCommand(this).execute();
    }

    @Override
    public User getUser(long id) throws Throwable {
        return new GetUserCommand(this, id).execute();
    }

    @Override
    public long countCollections() throws Throwable {
        ResultSet<Collection> r = listCollections(new GetCollectionsCommand.Params().setPerPage(1));
        return r.totalNumberOfResults();
    }

    @Override
    public long countElementSets() throws Throwable {
        ResultSet<ElementSet> r = listElementSets(new GetElementSetsCommand.Params().setPerPage(1));
        return r.totalNumberOfResults();
    }

    @Override
    public long countElements() throws Throwable {
        ResultSet<Element> r = listElements(new GetElementsCommand.Params().setPerPage(1));
        return r.totalNumberOfResults();
    }

    @Override
    public long countItemTypes() throws Throwable {
        ResultSet<ItemType> r = listItemTypes(new GetItemTypesCommand.Params().setPerPage(1));
        return r.totalNumberOfResults();
    }

    @Override
    public long countItems() throws Throwable {
        ResultSet<Item> r = listItems(new GetItemsCommand.Params().setPerPage(1));
        return r.totalNumberOfResults();
    }

    @Override
    public long countFiles() throws Throwable {
        ResultSet<File> r = listFiles(new GetFilesCommand.Params().setPerPage(1));
        return r.totalNumberOfResults();
    }

    @Override
    public long countTags() throws Throwable {
        ResultSet<Tag> r = listTags(new GetTagsCommand.Params().setPerPage(1));
        return r.totalNumberOfResults();
    }

}
