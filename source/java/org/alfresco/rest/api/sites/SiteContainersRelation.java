package org.alfresco.rest.api.sites;

import org.alfresco.query.PagingResults;
import org.alfresco.rest.api.Sites;
import org.alfresco.rest.api.model.SiteContainer;
import org.alfresco.rest.framework.WebApiDescription;
import org.alfresco.rest.framework.resource.RelationshipResource;
import org.alfresco.rest.framework.resource.actions.interfaces.RelationshipResourceAction;
import org.alfresco.rest.framework.resource.parameters.CollectionWithPagingInfo;
import org.alfresco.rest.framework.resource.parameters.Parameters;
import org.alfresco.util.ParameterCheck;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

@RelationshipResource(name = "containers", entityResource = SiteEntityResource.class, title = "Site Containers")
public class SiteContainersRelation implements RelationshipResourceAction.Read<SiteContainer>, RelationshipResourceAction.ReadById<SiteContainer>, InitializingBean
{
    private static final Log logger = LogFactory.getLog(SiteContainersRelation.class);

    private Sites sites;

	public void setSites(Sites sites)
	{
		this.sites = sites;
	}

	@Override
    public void afterPropertiesSet()
    {
        ParameterCheck.mandatory("sites", this.sites);
    }

	/**
	 * Returns information regarding the top level container 'containerId' for site 'siteId'.
	 * 
	 */
	@Override
    @WebApiDescription(title = "Site container information for container 'containerId' in site 'siteId'.")
	public SiteContainer readById(String siteId, String containerId, Parameters parameters)
	{
		return sites.getSiteContainer(siteId, containerId);
	}

	/**
	 * Returns information regarding the top level containers for site 'siteId'.
	 * 
	 */
	@Override
    @WebApiDescription(title = "A paged list of site containers.")
	public CollectionWithPagingInfo<SiteContainer> readAll(String siteId, Parameters parameters) 
	{
		PagingResults<SiteContainer> siteContainers = sites.getSiteContainers(siteId, parameters.getPaging());
		return CollectionWithPagingInfo.asPaged(parameters.getPaging(), siteContainers.getPage(), siteContainers.hasMoreItems(), siteContainers.getTotalResultCount().getFirst());
	}
}
