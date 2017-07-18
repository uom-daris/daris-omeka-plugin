package omeka.plugin.services;

import java.util.ArrayList;
import java.util.List;

import arc.mf.plugin.dtype.BooleanType;
import arc.mf.plugin.dtype.IntegerType;
import arc.mf.plugin.dtype.LongType;
import arc.xml.XmlDoc.Element;
import arc.xml.XmlWriter;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.ResultSet;
import io.github.xtman.omeka.model.Entity;

public abstract class OmekaEntityListPluginService<T extends Entity> extends OmekaPluginService {

    protected OmekaEntityListPluginService() {

        defn.add(new Interface.Element("idx", LongType.POSITIVE_ONE,
                "Absolute cursor position. Starts from 1. Defaults to 1.", 0, 1));
        defn.add(new Interface.Element("size", IntegerType.POSITIVE_ONE,
                "Number of results to return. Defaults to 100.", 0, 1));
        defn.add(new Interface.Element("detail", BooleanType.DEFAULT,
                "Include details about the entity. Defaults to true."));

    }

    @Override
    protected final void execute(OmekaClient omekaClient, Element args, Inputs inputs, Outputs outputs, XmlWriter w)
            throws Throwable {
        long idx = args.longValue("idx", 1L);
        int pageSize = args.intValue("size", 100);
        long pageIndex = (idx - 1) / pageSize + 1;
        int remainder = (int) ((idx - 1) % pageSize);
        boolean detail = args.booleanValue("detail", true);

        ResultSet<T> rs1 = listEntities(omekaClient, pageIndex, pageSize, args);
        long total = rs1.totalNumberOfResults();
        List<T> rs1Entities = rs1.entities();
        int rs1Size = rs1Entities == null ? 0 : rs1Entities.size();
        List<T> entities = new ArrayList<T>();
        if (rs1Size > 0) {
            if (remainder == 0) {
                entities.addAll(rs1Entities);
            } else {
                entities.addAll(rs1Entities.subList(remainder, rs1Entities.size()));
                if (rs1Size == pageSize) {
                    ResultSet<T> rs2 = listEntities(omekaClient, pageIndex + 1, pageSize, args);
                    List<T> rs2Entities = rs2.entities();
                    int rs2Size = rs2Entities == null ? 0 : rs2Entities.size();
                    if (rs2Size > 0) {
                        entities.addAll(rs2Entities.subList(0, rs2Size > remainder ? remainder : rs2Size));
                    }
                }
            }
        }
        describeEntities(idx, entities, total, w, detail);

    }

    protected abstract ResultSet<T> listEntities(OmekaClient omekaClient, long pageIndex, int pageSize, Element args)
            throws Throwable;

    protected void describeEntities(long idx, List<T> entities, long total, XmlWriter w, boolean detail) throws Throwable {
        if (entities != null) {
            for (T entity : entities) {
                describeEntity(entity, w, detail);
            }
        }
        w.push("cursor");
        long count = entities == null ? 0 : entities.size();
        w.add("count", count);
        long from = idx > total ? 0 : idx;
        w.add("from", from);
        long to = from > 0 ? from + count - 1 : 0;
        w.add("to", to);
        if (idx > 1) {
            w.add("prev", idx - 1);
        }
        boolean completed = total == 0 || (idx + count - 1 >= total);
        if (!completed) {
            w.add("next", to + 1);
        }
        w.add("total", new String[] { "completed", Boolean.toString(completed) }, total);
        w.pop();
    }

    protected abstract void describeEntity(T entity, XmlWriter w, boolean detail) throws Throwable;

    @Override
    public Access access() {
        return ACCESS_ACCESS;
    }

    @Override
    protected boolean requiresApiKey() {
        return false;
    }

}
