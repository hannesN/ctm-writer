# The CTM Topic Map Writer #

The Compact Topic Maps ( CTM ) is a text-based representation of a topic map and describes a standardized interchange format of topic maps. The syntax is designed in a human-readable way and represents any information items and relationships between items modeled by the topic maps data model ( TMDM ) as simple test patterns.

A community project, called TMAPIx, wants to provide additional features on top of the core TMAPI functions, like realizing some import or export functionality supporting XTM or CTM. This efforts are designed in a similar way to TMAPI, as should be independent form a specific implementation and only operates on core interfaces of the TMAPI.

This implementation of a CTM Writer provides the core functionality of exporting every topic map to CTM.

The main benefits of the current CTM topic map writer implementation are:

  * The code is completely open source and can be modified

  * The writer works on top of the TMAPI and is independent of the real topic map engine implementation ( works with Ontopia, tinyTim etc. )

  * The writer supports the partial serialization of topic map fragments ( topics, associations etc. )

  * The writer supports additional features like prefix- and template-detection

For more information, take a look at the [documentation](http://docs.topicmapslab.de/ctm-writer).