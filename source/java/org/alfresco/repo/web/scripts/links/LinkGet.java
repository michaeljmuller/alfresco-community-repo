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
package org.alfresco.repo.web.scripts.links;

import java.util.HashMap;
import java.util.Map;

import org.alfresco.service.cmr.links.LinkInfo;
import org.alfresco.service.cmr.site.SiteInfo;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

/**
 * This class is the controller for the link fetching link.get webscript.
 * 
 * @author Nick Burch
 * @since 4.0
 */
public class LinkGet extends AbstractLinksWebScript
{
   @Override
   protected Map<String, Object> executeImpl(SiteInfo site, String linkName,
         WebScriptRequest req, JSONObject json, Status status, Cache cache) {
      Map<String, Object> model = new HashMap<String, Object>();
      
      // Try to find the link
      LinkInfo link = linksService.getLink(site.getShortName(), linkName);
      if(link == null)
      {
         String message = "No link found with that name";
         throw new WebScriptException(Status.STATUS_NOT_FOUND, message);
      }
      
      // Build the model
      model.put("item", renderLink(link));
      model.put("node", link.getNodeRef());
      model.put("link", link);
      model.put("site", site);
      model.put("siteId", site.getShortName());
      
      // All done
      return model;
   }
}
