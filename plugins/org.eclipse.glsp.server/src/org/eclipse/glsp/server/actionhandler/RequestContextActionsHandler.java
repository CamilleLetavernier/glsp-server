/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package org.eclipse.glsp.server.actionhandler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.RequestContextActions;
import org.eclipse.glsp.api.action.kind.SetContextActions;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.registry.ContextActionsProviderRegistry;
import org.eclipse.glsp.api.types.EditorContext;
import org.eclipse.glsp.api.types.LabeledAction;

import com.google.inject.Inject;

public class RequestContextActionsHandler extends BasicActionHandler<RequestContextActions> {

   @Inject
   protected ContextActionsProviderRegistry contextActionsProviderRegistry;

   @Override
   public List<Action> executeAction(final RequestContextActions action, final GraphicalModelState modelState) {
      EditorContext editorContext = action.getEditorContext();
      List<LabeledAction> actions = new ArrayList<>();
      contextActionsProviderRegistry.get(action.getContextId())
         .map(provider -> provider.getActions(editorContext, modelState))
         .ifPresent(labeledActions -> actions.addAll(labeledActions));

      return listOf(new SetContextActions(actions, action.getEditorContext().getArgs()));
   }
}
