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

public interface Document {
	
	/**
	 * Changes the type of a document. This is a version-level property.
	 * @param documentId
	 * @param versionId
	 * @param docTypeId
	 * @throws RepositoryException
	 */
	public void changeDocumentType(long documentId, long versionId, long docTypeId) throws RepositoryException;
	
	
	/**
	 * 
	 * @param documentId
	 * @param versionId
	 * @throws RepositoryException
	 */
	public void addLink (long documentId, long versionId) throws RepositoryException;

}
