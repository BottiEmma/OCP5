describe('Me spec', () => {
  it('Show account informations', () => {
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

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    cy.intercept('GET', '/api/user/1', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        email: 'yoga@studio.com',
        admin: true
      },
    })
    cy.get('[routerlink="me"]').click();
    cy.url().should('include', '/me');
    cy.contains('p', 'Name: firstName LASTNAME').should('exist');
    cy.contains('p', 'Email: yoga@studio.com').should('exist');
    cy.contains('p.my2', 'You are admin').should('exist');

  })
});
