/*
 * Copyright (C) 2005-2011 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alfresco.module.org_alfresco_module_rm.capability.declarative.condition;

import org.alfresco.module.org_alfresco_module_rm.capability.declarative.AbstractCapabilityCondition;
import org.alfresco.module.org_alfresco_module_rm.disposition.DispositionAction;
import org.alfresco.module.org_alfresco_module_rm.disposition.DispositionService;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Indicates whether the given disposition action 'may' be scheduled in the future
 * 
 * @author Roy Wetherall
 */
public class IsScheduledCapabilityCondition extends AbstractCapabilityCondition
{
    /** Disposition action */
    private String dispositionAction;
    
    /** Disposition service */
    private DispositionService dispositionService;
    
    /**
     * @param dispositionAction     disposition action
     */
    public void setDispositionAction(String dispositionAction)
    {
        this.dispositionAction = dispositionAction;
    }
    
    /**
     * @param dispositionService    disposition service
     */
    public void setDispositionService(DispositionService dispositionService)
    {
        this.dispositionService = dispositionService;
    }
    
    /**
     * @see org.alfresco.module.org_alfresco_module_rm.capability.declarative.CapabilityCondition#evaluate(org.alfresco.service.cmr.repository.NodeRef)
     */
    @Override
    public boolean evaluate(NodeRef nodeRef)
    {
        boolean result = false;        
        
        DispositionAction nextDispositionAction = dispositionService.getNextDispositionAction(nodeRef);
        if (nextDispositionAction != null)
        {
            // Get the disposition actions name
            String actionName = nextDispositionAction.getName();            
            if (actionName.equals(dispositionAction) == true &&
                dispositionService.isNextDispositionActionEligible(nodeRef) == true)
            {
                result = true;                
            }
        }

        return result;
    }
}
