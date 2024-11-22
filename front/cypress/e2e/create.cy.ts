describe('Create spec', () => {
  it('Create session', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept('GET', '/api/session', {
      body: [
        {
          id: 1,
          name: 'test',
          teacher_id: 1,
          description: 'desc',
        },
      ],
    }).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.wait('@session');
    cy.url().should('include', '/sessions')
    cy.intercept('GET', '/api/teacher', {
      body: [{
        id: 1,
        firstName: 'Margot',
        lastName: 'DELAHAYE',
      }],
    })
    cy.intercept('GET', '/api/teacher/1', {
      body: {
        id: 1,
        firstName: 'Margot',
        lastName: 'DELAHAYE',
      }
    }).as('teacherDetail')
    cy.intercept('POST', '/api/session', {
      body:
        {
          id: 1,
          name: 'test',
          teacher_id: 1,
          description: 'desc',
        },
    }).as('sessionDetail')
    cy.contains('Create').click();
    cy.url().should('include', '/create');
    cy.get('input[formControlName=name]').type("session")
    cy.get('input[formControlName=date]').type("2024-11-15")
    cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('Margot DELAHAYE').click();
    cy.get('textarea[formControlName=description]').type("desc")
    cy.get('button[type=submit]').click()
    cy.url().should('include', '/sessions')
  })
});
