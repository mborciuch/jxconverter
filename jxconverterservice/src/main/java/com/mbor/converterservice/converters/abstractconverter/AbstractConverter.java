package com.mbor.converterservice.converters.abstractconverter;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.ComponentNode;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.factories.nodes.NodeFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;

public abstract class AbstractConverter<T extends InputExtractionResult> implements Converter {

    private NodeFactory nodeFactory;
    private ExecutorService executorService;

    public AbstractConverter(NodeFactory nodeFactory, ExecutorService executorService) {
        this.nodeFactory = nodeFactory;
        this.executorService = executorService;
    }

    protected NodeFactory getNodeFactory() {
        return nodeFactory;
    }

    protected ExecutorService getExecutorService() {
        return executorService;
    }

    protected ComponentNode prepareComponentNode(AbstractNode abstractNode) {
        ComponentNode componentNode;
        if (abstractNode instanceof Node) {
            if (abstractNode.hasAttributes()) {
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

    protected boolean isInputExtractionResultTheSameLevelList(List<T> resultList) {
        if (resultList.size() == 0) {
            throw new RuntimeException("ExtractionResultList is Empty");
        }
        return resultList.size() > 1;
    }

    protected abstract boolean isInputExtractionResultLeaf(InputExtractionResult inputExtractionResult);
}
