package com.mycompany;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
    private Label sessionType;
    private String password;
    private String username;
    private Integer radioSelected;

    public HomePage(final PageParameters parameters) {
        StatelessForm<Void> form = new StatelessForm<>("form") {
            @Override
            protected void onSubmit() {
                //sign in if username and password are “user”
                if ("user".equals(username) && username.equals(password))
                    info("Username and password are correct!");
                else
                    error("Wrong username or password");
            }
        };

        form.add(new PasswordTextField("password"));
        form.add(new TextField<>("username"));

        RadioGroup<Integer> radioGroup = new RadioGroup<>("radioGroup",
                LambdaModel.of(() -> radioSelected, newValue -> radioSelected = newValue));
        form.add(radioGroup);
        radioGroup.add(createRadio("radio1", Model.of(1)));
        radioGroup.add(createRadio("radio2", Model.of(2)));

        add(form.setDefaultModel(new CompoundPropertyModel<>(this)));

        add(sessionType = new Label("sessionType", Model.of("")));
        add(new FeedbackPanel("feedbackPanel"));
    }

    private Radio<Integer> createRadio(final String wicketId, IModel<Integer> model) {
        return new Radio<>(wicketId, model) {
            @Override
            protected boolean getStatelessHint() {
                return true;
            }
        };
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        if (getSession().isTemporary())
            sessionType.setDefaultModelObject("temporary");
        else
            sessionType.setDefaultModelObject("permanent");
    }
}