package com.mbor.converterservice.converters.abstractconverter;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.ComponentNode;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.factories.nodes.NodeFactory;

import java.util.List;

public abstract class AbstractConverter <T extends InputExtractionResult> implements Converter {

    private NodeFactory nodeFactory;

    public AbstractConverter(NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public NodeFactory getNodeFactory() {
        return nodeFactory;
    }

     protected ComponentNode prepareComponentNode(AbstractNode abstractNode) {
         ComponentNode componentNode;
         if (abstractNode instanceof Node) {
             if(abstractNode.hasAttributes()){
                 componentNode = getNodeFactory().getComponentNodeWithNodeList();
                 componentNode.setAbstractNode(abstractNode);
             } else {
                 componentNode = getNodeFactory().getComponentNodeWithNode();
                 componentNode.setAbstractNode(abstractNode);
             }
         } else {
             componentNode = getNodeFactory().getComponentNodeWithNodeList();
             componentNode.setAbstractNode(abstractNode);
         }
         return componentNode;
     }

     protected boolean isInputExtractionResultTheSameLevelList(List<T> resultList){
         if(resultList.size() == 0){
             throw new RuntimeException("ExtractionResultList is Empty");
         }
         return resultList.size() > 1;
     }

     protected abstract  boolean isInputExtractionResultLeaf(InputExtractionResult inputExtractionResult);

}
