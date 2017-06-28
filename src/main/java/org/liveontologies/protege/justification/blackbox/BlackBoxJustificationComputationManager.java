package org.liveontologies.protege.justification.blackbox;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

/*-
 * #%L
 * Protege Blackbox Justification Explanation
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2016 - 2017 Live Ontologies Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.liveontologies.protege.explanation.justification.service.JustificationComputation;
import org.liveontologies.protege.explanation.justification.service.JustificationComputation.InterruptMonitor;
import org.liveontologies.protege.explanation.justification.service.JustificationComputationManager;
import org.liveontologies.protege.explanation.justification.service.JustificationListener;
import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Alexander Stupnikov Date: 22/06/2017
 */
public class BlackBoxJustificationComputationManager
		extends JustificationComputationManager {

	private final SettingsPanel panel_;
	private final WorkbenchSettings workbenchSettings_;
	private final OWLEditorKit kit_;

	private JustificationComputation computation_;

	public BlackBoxJustificationComputationManager(OWLAxiom entailment,
			JustificationListener listener, InterruptMonitor monitor,
			OWLEditorKit kit) {
		super(entailment, listener, monitor);
		kit_ = kit;
		panel_ = new SettingsPanel();
		workbenchSettings_ = new WorkbenchSettings();
		recreateComputation();
	}

	private void recreateComputation() {
		computation_ = new BlackBoxJustificationComputation(
				getJustificationListener(), getInterruptMonitor(),
				getEntailment(), kit_, workbenchSettings_);
		notifyComputationChanged();
	}

	@Override
	public JustificationComputation getComputation() {
		return computation_;
	}

	@Override
	public JPanel getSettingsPanel() {
		return panel_;
	}

	public class SettingsPanel extends JPanel {
		private static final long serialVersionUID = -7739713369763932582L;

		public SettingsPanel() {
			JComboBox<String> cb = new JComboBox<String>();
			cb.addItem("Regular justifications");
			cb.addItem("Laconic justifications");
			cb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					workbenchSettings_
							.setJustificationType((cb.getSelectedIndex() == 0
									? JustificationType.REGULAR
									: JustificationType.LACONIC));
					recreateComputation();
				}
			});
			setLayout(new BorderLayout());
			add(cb);
		}
	}
}