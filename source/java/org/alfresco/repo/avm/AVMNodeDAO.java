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
package org.alfresco.repo.avm;

import java.util.Iterator;
import java.util.List;

/**
 * DAO for AVMNodes interface.
 * @author britt
 */
public interface AVMNodeDAO
{
    /**
     * Save the given node, having never been saved before.
     */
    public void save(AVMNode node);
    
    /**
     * Make all the nodes owned by a repository no longer
     * point to that repository.
     * @param rep The Repository.
     */
    public void dereferenceNodesInRepository(Repository rep);
    
    /**
     * Delete a single node.
     * @param node The node to delete.
     */
    public void delete(AVMNode node);
    
    /**
     * Get by ID.
     * @param id The id to get.
     */
    public AVMNode getByID(long id);

    /**
     * Get the root of a particular version.
     * @param rep The repository we're querying.
     * @param version The version.
     * @return The VersionRoot or null.
     */
    public DirectoryNode getRepositoryRoot(Repository rep, int version);

    /**
     * Get those nodes which are new in the given repository.
     * @param repo The repository.
     * @return A List of AVMNodes.
     */
    public List<AVMNode> getNewInRepo(Repository repo);
    
    /**
     * Update a node that has been dirtied.
     * @param node The node.
     */
    public void update(AVMNode node);
    
    /**
     * Get the ancestor of a node.
     * @param node The node whose ancestor is desired.
     * @return The ancestor or null.
     */
    public AVMNode getAncestor(AVMNode node);
    
    /**
     * Get the node the given node was merged from.
     * @param node The node whose merged from is desired.
     * @return The merged from node or null.
     */
    public AVMNode getMergedFrom(AVMNode node);
    
    /**
     * Get up to batchSize orphans. 
     * @param batchSize Get no more than this number.
     * @return A List of orphaned AVMNodes.
     */
    public List<AVMNode> getOrphans(int batchSize);
    
    /**
     * Get all nodes that have the given repository as their owning repository.
     * @param rep The Repository.
     * @return An Iterator over the matching nodes.
     */
    public Iterator<AVMNode> getByRepository(Repository rep);
    
    /**
     * Inappropriate hack to get Hibernate to play nice.
     */
    public void flush();
}
