/*
 * Copyright (C) 2006 Alfresco, Inc.
 *
 * Licensed under the Mozilla Public License version 1.1 
 * with a permitted attribution clause. You may obtain a
 * copy of the License at
 *
 *   http://www.alfresco.org/legal/license.txt
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 */

package org.alfresco.repo.avm.hibernate;

import java.util.List;

import org.alfresco.repo.avm.Repository;
import org.alfresco.repo.avm.RepositoryDAO;
import org.alfresco.repo.avm.RepositoryImpl;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * The Hibernate version for RepositoryDAO
 * @author britt
 */
public class RepositoryDAOHibernate extends HibernateDaoSupport implements
        RepositoryDAO
{
    /**
     * Do nothing constructor.
     */
    public RepositoryDAOHibernate()
    {
        super();
    }
    
    /**
     * Save a repository, never before saved.
     * @param rep The repository
     */
    public void save(Repository rep)
    {
        getSession().save(rep);
    }

    /**
     * Delete the given Repository.
     * @param rep The Repository.
     */
    public void delete(Repository rep)
    {
        getSession().delete(rep);
    }

    /**
     * Get all repositories.
     * @return A List of all the Repositories.
     */
    @SuppressWarnings("unchecked")
    public List<Repository> getAll()
    {
        Query query = getSession().createQuery("from RepositoryImpl r");
        return (List<Repository>)query.list();
    }
    
    /**
     * Get a repository by name.
     * @param name The name of the repository.
     * @return The repository or null if not found.
     */
    public Repository getByName(String name)
    {
        return (Repository)getSession().get(RepositoryImpl.class, name);
    }
    
    /**
     * Update the given Repository record.
     * @param rep The dirty Repository.
     */
    public void update(Repository rep)
    {
        // No op in Hibernate.
    }
}
