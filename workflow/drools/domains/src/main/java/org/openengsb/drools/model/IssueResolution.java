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

package org.openengsb.drools.model;

/**
 * Describes the possibilities for resolution
 */
public class IssueResolution {
    public static final int OPEN = 0;
    public static final int REOPENED = 1;
    public static final int FIXED = 2;
    public static final int INVALID = 3;
    public static final int WONTFIX = 4;
    public static final int DUPLICATE = 5;
    public static final int WORKSFORM = 6;
    public static final int UNABLETOPRODUCE = 7;
    public static final int NOTFIXABLE = 8;
    public static final int NOCHANGEREQUIRED = 9;
    public static final int SUSPENDED = 10;
}
