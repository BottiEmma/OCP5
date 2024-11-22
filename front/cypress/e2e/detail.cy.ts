describe('Detail spec', () => {
  it('Show session detail', () => {
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
    cy.intercept('GET', '/api/session/1', {
      body:
        {
          id: 1,
          name: 'test',
          teacher_id: 1,
          description: 'desc',
        },
    }).as('sessionDetail')
    cy.contains('Detail').click();
    cy.url().should('include', '/detail/1');
  })
});
