asset.doc.namespace.update :create true :namespace omeka :description "Document namespace for OMEKA document types"
asset.doc.type.update \
    :type omeka:omeka-item-identity \
    :create true \
    :label "OMEKA Item Identity" \
    :description "OMEKA item identity information." \
    :definition < \
        :element -name item -label "OMEKA Item ID" -min-occurs 1 -max-occurs infinity -type long -index true < \
            :description "OMEKA item id" \
            :attribute -name site -label "OMEKA Site Title" -min-occurs 1 -type string -index true  < :description "OMEKA site title" > \
            :attribute -name collection -label "OMEKA Collection ID" -min-occurs 0 -type long -index true < :description "OMEKA collection id" > \
            :attribute -name url -label "OMEKA Item URL" -min-occurs 1 -type url -index true < :description "OMEKA item url" > > >

asset.doc.type.update \
    :type omeka:omeka-file-identity \
    :create true \
    :label "OMEKA file identity" \
    :description "OMEKA file identity information." \
    :definition < \
        :element -name file -label "OMEKA File" -min-occurs 1 -max-occurs infinity -type long -index true < \
            :description "OMEKA file id" \
            :attribute -name site -label "OMEKA Site Title" -min-occurs 1 -type string -index true  < :description "OMEKA site title" > \
            :attribute -name item -label "OMEKA Item ID" -min-occurs 1 -type long -index true < :description "OMEKA item id" > \
            :attribute -name url -label "OMEKA File URL" -min-occurs 1 -type url -index true < :description "OMEKA file url" > > >



