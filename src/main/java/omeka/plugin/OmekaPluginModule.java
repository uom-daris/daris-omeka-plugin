package omeka.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import arc.mf.plugin.ConfigurationResolver;
import arc.mf.plugin.PluginService;
import omeka.plugin.services.SvcCollectionCreate;
import omeka.plugin.services.SvcCollectionDescribe;
import omeka.plugin.services.SvcCollectionDelete;
import omeka.plugin.services.SvcCollectionList;
import omeka.plugin.services.SvcCollectionUpdate;
import omeka.plugin.services.SvcElementCreate;
import omeka.plugin.services.SvcElementDescribe;
import omeka.plugin.services.SvcElementDelete;
import omeka.plugin.services.SvcElementList;
import omeka.plugin.services.SvcElementSetCreate;
import omeka.plugin.services.SvcElementSetDescribe;
import omeka.plugin.services.SvcElementSetDelete;
import omeka.plugin.services.SvcElementSetList;
import omeka.plugin.services.SvcElementSetUpdate;
import omeka.plugin.services.SvcElementUpdate;
import omeka.plugin.services.SvcFileCreate;
import omeka.plugin.services.SvcFileDescribe;
import omeka.plugin.services.SvcFileDelete;
import omeka.plugin.services.SvcFileList;
import omeka.plugin.services.SvcFileUpdate;
import omeka.plugin.services.SvcItemCreate;
import omeka.plugin.services.SvcItemDescribe;
import omeka.plugin.services.SvcItemDelete;
import omeka.plugin.services.SvcItemList;
import omeka.plugin.services.SvcItemTypeCreate;
import omeka.plugin.services.SvcItemTypeDescribe;
import omeka.plugin.services.SvcItemTypeDelete;
import omeka.plugin.services.SvcItemTypeList;
import omeka.plugin.services.SvcItemTypeUpdate;
import omeka.plugin.services.SvcItemUpdate;
import omeka.plugin.services.SvcResourcesDescribe;
import omeka.plugin.services.SvcSiteDescribe;
import omeka.plugin.services.SvcTagDescribe;
import omeka.plugin.services.SvcTagDelete;
import omeka.plugin.services.SvcTagList;
import omeka.plugin.services.SvcUserDescribe;

public class OmekaPluginModule implements arc.mf.plugin.PluginModule {

    private List<PluginService> _services;

    public OmekaPluginModule() {
        _services = new ArrayList<PluginService>();
        _services.add(new SvcSiteDescribe());
        _services.add(new SvcResourcesDescribe());
        _services.add(new SvcUserDescribe());

        _services.add(new SvcCollectionCreate());
        _services.add(new SvcCollectionDescribe());
        _services.add(new SvcCollectionList());
        _services.add(new SvcCollectionDelete());
        _services.add(new SvcCollectionUpdate());

        _services.add(new SvcElementSetCreate());
        _services.add(new SvcElementSetDescribe());
        _services.add(new SvcElementSetList());
        _services.add(new SvcElementSetDelete());
        _services.add(new SvcElementSetUpdate());

        _services.add(new SvcElementCreate());
        _services.add(new SvcElementDescribe());
        _services.add(new SvcElementList());
        _services.add(new SvcElementDelete());
        _services.add(new SvcElementUpdate());

        _services.add(new SvcItemTypeCreate());
        _services.add(new SvcItemTypeDescribe());
        _services.add(new SvcItemTypeList());
        _services.add(new SvcItemTypeDelete());
        _services.add(new SvcItemTypeUpdate());

        _services.add(new SvcItemCreate());
        _services.add(new SvcItemDescribe());
        _services.add(new SvcItemList());
        _services.add(new SvcItemDelete());
        _services.add(new SvcItemUpdate());

        _services.add(new SvcFileCreate());
        _services.add(new SvcFileDescribe());
        _services.add(new SvcFileList());
        _services.add(new SvcFileDelete());
        _services.add(new SvcFileUpdate());

        _services.add(new SvcTagDescribe());
        _services.add(new SvcTagList());
        _services.add(new SvcTagDelete());
    }

    @Override
    public String description() {
        return "Plugin package to integrate OMEKA REST client. It includes plugin services and data sinks to communicate with remote OMEKA server.";
    }

    @Override
    public void initialize(ConfigurationResolver conf) throws Throwable {

    }

    @Override
    public Collection<PluginService> services() {
        return _services;
    }

    @Override
    public void shutdown(ConfigurationResolver conf) throws Throwable {

    }

    @Override
    public String vendor() {
        return "The University of Melbourne";
    }

    @Override
    public String version() {
        return Version.VERSION;
    }

}
