# ============================================================================
# Install Plugins
# ============================================================================
set plugin_label           [string toupper PACKAGE_$package]
set plugin_namespace       /mflux/plugins/daris-omeka-plugin
set plugin_zip             daris-omeka-plugin.zip
set plugin_jar             daris-omeka-plugin.jar
set module_class           omeka.plugin.OmekaPluginModule

# extract transform-plugin.jar to /mflux/plugins
asset.import :url archive:${plugin_zip} \
        :namespace -create yes ${plugin_namespace} \
        :label -create yes ${plugin_label} :label PUBLISHED \
        :update true

# remove the plugin module if pre-exists. Otherwise, cannot register the sink type.
if { [xvalue exists [plugin.module.exists :path ${plugin_namespace}/${plugin_jar} :class ${module_class}]] == "true" } {
    puts "Removing existing plugin module: ${module_class}"
	plugin.module.remove :path ${plugin_namespace}/${plugin_jar} :class ${module_class}
}

# install the plugin module
plugin.module.add :path ${plugin_namespace}/${plugin_jar} :class ${module_class} \
    :lib ${plugin_namespace}/lib/json-20160810.jar

# reload the services     
system.service.reload

# refresh the enclosing shell
srefresh

# create/update doc types
source doc-types.tcl
