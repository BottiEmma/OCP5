describe('Participate spec', () => {
  it('Show account informations for a user', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false
      },
    })
    cy.intercept('GET', '/api/teacher/2', {
      body: {
        id: 2,
        firstName: 'Margot',
        lastName: 'DELAHAYE',
      }
    }).as('teacherDetail')

    cy.intercept('GET', '/api/session', {
      body: [
        {
          id: 1,
          name: 'test',
          teacher_id: 2,
          description: 'desc',
        },
      ],
    }).as('session')


    cy.get('input[formControlName=email]').type("test@mail.com")
    cy.get('input[formControlName=password]').type(`${"password"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    cy.intercept('GET', '/api/session/1', {
      body:
        {
          id: 1,
          name: 'test',
          description: 'desc',
          date: '2024-07-30T10:00:00.000Z',
          teacher_id: 2,
          users: []
        },
    }).as('sessionDetail')
    cy.contains('Detail').click();
    cy.url().should('include', '/detail/1');
    cy.get('button').contains('Delete').should('not.exist');
    cy.get('button').contains('Participate').should('exist');
    cy.intercept('POST', '/api/session/1/participate/1', {
      body:
        {
          user_id: 1,
          session_id: 1
        },
    })
    cy.intercept('GET', '/api/session/1', {
      body:
        {
          id: 1,
          name: 'test',
          description: 'desc',
          date: '2024-07-30T10:00:00.000Z',
          teacher_id: 2,
          users: [1]
        },
    }).as('sessionDetail')
    cy.contains('', '0 attendees').should('exist');
    cy.contains('Participate').click();
    cy.get('button').contains('Do not participate').should('exist');
    cy.contains('', '1 attendees').should('exist');
  });
});
