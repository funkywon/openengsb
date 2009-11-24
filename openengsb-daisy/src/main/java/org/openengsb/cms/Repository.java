/**

   Copyright 2009 OpenEngSB Division, Vienna University of Technology

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
 */

package org.openengsb.cms;

public interface Repository {
	
	/**
	 * creates a new document
	 * @param name Name of the document, which does not have to be unique in the repository
	 * @param documentTypeId Id, of which type of document has to be created
	 * @return The newly created document
	 */
	public Document createDocument (String name, long documentTypeId);
	
	/**
	 * Deletes the given document
	 * @param documentId The id of the document to be deleted
	 * @throws RepositoryException Thrown if the id can not be found
	 */
	public void deleteDocument(long documentId) throws RepositoryException;
	
	
	/**
	 * Creates a new version of the given document 
	 * @param documentId
	 * @param copyContent True, if the content of the document should be copied to the returned element. 
	 * @return the new element
	 * @throws RepositoryException if the document can not be found
	 */
	public Document createVersion(long documentId, boolean copyContent) throws RepositoryException;
	
	/**
	 * Deletes a version of a document
	 * @param documentId document to be deleted
	 * @param versionId version to be deleted
	 * @throws RepositoryException Thrown, if the document/version can't be found
	 */
	public void deleteVersion(long documentId, long versionId) throws RepositoryException;
	
	
	/**
	 * Gets a document from the repository
	 * @param documentId 
	 * @param versionId
	 * @return
	 * @throws RepositoryException Thrown, if the document/version can't be found.
	 */
	public Document getDocument(long documentId, long versionId) throws RepositoryException;
	
	
	

}
