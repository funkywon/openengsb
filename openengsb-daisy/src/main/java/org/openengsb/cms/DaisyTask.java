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

import org.openengsb.cms.MantisTask;
import org.outerj.daisy.repository.CollectionManager;
import org.outerj.daisy.repository.Document;
import org.outerj.daisy.repository.DocumentCollection;
import org.outerj.daisy.repository.DocumentTypeInconsistencyException;
import org.outerj.daisy.repository.RepositoryException;
import org.outerj.daisy.repository.RepositoryManager;
import org.outerj.daisy.repository.Credentials;
import org.outerj.daisy.repository.Repository;
import org.outerj.daisy.repository.query.QueryManager;
import org.outerj.daisy.repository.query.ResultSet;
import org.outerj.daisy.repository.clientimpl.RemoteRepositoryManager;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.IssueNoteData;
import biz.futureware.mantisconnect.ObjectRef;

import org.outerx.daisy.x10.SearchResultDocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DaisyTask {
	
	private RepositoryManager repositoryManager;
	private Repository repository;
	private CollectionManager collectionManager;
	private void init() {
		try {
			repositoryManager = new RemoteRepositoryManager("http://localhost:9263", new Credentials("funkywon", "ghghgh"));
			repository = repositoryManager.getRepository(new Credentials("funkywon", "ghghgh"));
			repository.setActiveRoleIds(new long[]{1});
			collectionManager = repository.getCollectionManager();
			
			
			
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		DaisyTask task = new DaisyTask();
		task.init();
		MantisTask mantisTask = new MantisTask();
		IssueData[] issues = mantisTask.getIssues();
		
		for(IssueData issue : issues) {
			task.addDocument(issue);
		}
		
		
		
//		QueryManager queryManager = task.repository.getQueryManager();		
//		SearchResultDocument searchresults = queryManager.performQuery(
//				"select id, name where true", Locale.getDefault());
//		List<SearchResultDocument.SearchResult.Rows.Row> list = searchresults
//				.getSearchResult().getRows().getRowList();
//		SearchResultDocument.SearchResult.Rows rows = searchresults.getSearchResult().getRows();
//		SearchResultDocument.SearchResult searchresult = searchresults.getSearchResult();
//		SearchResultDocument.SearchResult.Titles titles = searchresult.getTitles();
//		List<SearchResultDocument.SearchResult.Titles.Title> titleList = titles.getTitleList();
//		for(SearchResultDocument.SearchResult.Titles.Title title:titleList) {
//			System.out.println("title name: "+title.getName());
//			System.out.println("title string value: "+title.getStringValue());
//		}
//		for (SearchResultDocument.SearchResult.Rows.Row row : list) {
//			System.out.println(row.getValueArray(0));
//			System.out.println(row.getValueArray(1));
//		}
	}
	
	private void addDocument(IssueData issue) {
		
		
		Document document = repository.createDocument("Issue_"+issue.getId().toString(),"Mantis_Issue");
		
		try {
			DocumentCollection col = collectionManager.getCollectionByName("prototyp", true);
			DocumentCollection notesCollection = collectionManager.createCollection("Issuenotes_"+issue.getId().toString());
			notesCollection.save();

			
			HashMap<String,Object> map = new HashMap<String,Object>();
			String[] fields = new String[] {"Issue_Category","Issue_Severity","Issue_OS_Version",
					"Issue_Summary","Issue_Description","Issue_Reproducibility","Issue_Priority",
					"Issue_Platform","Issue_OS","Issue_Product_Version","Issue_Product_Build","Issue_Assign_To",
					"Issue_Reporter","Issue_Target_Version","Issue_Steps_To_Produce","Issue_Additional_Information","Issue_View_Status","Issue_Id"};
			
			
			map.put("Issue_Category",issue.getCategory());
			map.put("Issue_OS_Version",issue.getOs_build());
			map.put("Issue_Summary",issue.getSummary());
			map.put("Issue_Description",issue.getDescription());
			map.put("Issue_Platform", issue.getPlatform());
			map.put("Issue_OS", issue.getOs());
			map.put("Issue_Product_Version", issue.getVersion());
			map.put("Issue_Product_Build", issue.getBuild());
			map.put("Issue_Target_Version", issue.getTarget_version());
			map.put("Issue_Additional_Information", issue.getAdditional_information());
			map.put("Issue_Steps_To_Produce", issue.getSteps_to_reproduce());
			map.put("Issue_Id", issue.getId().longValue());
			
			List<ObjectRef> list = new ArrayList<ObjectRef>();
			
			if(issue.getSeverity()!=null)
				map.put("Issue_Severity",issue.getSeverity().getName());
			else map.put("Issue_Severity","");
			if(issue.getReproducibility()!=null)
				map.put("Issue_Reproducibility", issue.getReproducibility().getName());
			else map.put("Issue_Reproducibility","");
			
			if(issue.getPriority()!=null)
				map.put("Issue_Priority", issue.getPriority().getName());
			else map.put("Issue_Priority","");
			
			if(issue.getHandler()!=null)
				map.put("Issue_Assign_To", issue.getHandler().getName());
			else map.put("Issue_Assign_To","");
			
			if(issue.getReporter()!=null)
				map.put("Issue_Reporter", issue.getReporter().getName());
			else map.put("Issue_Reporter","");
			
			if(issue.getView_state()!=null)
				map.put("Issue_View_Status", issue.getStatus().getName());
			else map.put("Issue_View_Status","");
			
			for (String field:fields) {
				if(map.get(field)!=null) {
					document.setField(field, map.get(field));
				} else {
					document.setField(field, "");
				}
			}
			document.addToCollection(col);
			
			
			IssueNoteData[] notes = issue.getNotes();
			if(notes!=null) {
				for(IssueNoteData note:notes) {
					Document noteDoc = repository.createDocument("Issuenote_"+note.getId().toString(),"Mantis_IssueNote");
					noteDoc.setField("Issuenote_Id", note.getId().longValue());
					noteDoc.setField("Issuenote_DateLastModified", note.getLast_modified().getTime());
					noteDoc.setField("Issuenote_DateSubmitted", note.getDate_submitted().getTime());
					noteDoc.setField("Issuenote_IssueId", issue.getId().longValue());
					noteDoc.setField("Issuenote_Text", note.getText());
					noteDoc.setField("Issuenote_Reporter", note.getReporter().getName());
					noteDoc.setField("Issuenote_Viewstate", note.getView_state().getName());
					noteDoc.addToCollection(notesCollection);
					noteDoc.addToCollection(col);
					noteDoc.save();
				}
			}
			

			document.save();
		} catch (DocumentTypeInconsistencyException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}  
		
	}
}