/**

   Copyright 2010 OpenEngSB Division, Vienna University of Technology

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
package org.openengsb.config.view;

import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.basic.Label;
import org.openengsb.config.editor.EditorPanel;
import org.openengsb.config.jbi.ServiceUnitInfo;
import org.openengsb.config.jbi.types.ComponentType;
import org.openengsb.config.jbi.types.EndpointType;

public class ComponentEditorPage extends BasePage {

    public ComponentEditorPage(PageParameters params) {
        this(params.getString("component"), params.getString("endpoint"));
    }

    public ComponentEditorPage(String componentName, String endpointName) {
        final ComponentType desc = componentService.getComponent(componentName);
        add(new Label("name", desc.getName()));
        add(new Label("namespace", desc.getNamespace()));

        EndpointType ee = null;
        for (EndpointType e : desc.getEndpoints()) {
            if (e.getName().equals(endpointName)) {
                ee = e;
                break;
            }
        }
        final EndpointType endpoint = ee;

        EditorPanel editor = new EditorPanel("editor", desc.getName(), endpoint) {
            @Override
            public void onSubmit() {
                assemblyService.getServiceUnits().add(new ServiceUnitInfo(desc, endpoint, getValues()));
                RequestCycle.get().setResponsePage(CreateAssemblyPage.class);
            }
        };
        add(editor);
    }
}
