describe('Me spec', () => {
  it('Show account informations for an admin', () => {
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
    cy.contains('p', 'Delete my account:').should('not.exist');
    cy.contains('button.mat-raised-button.mat-warn', 'Detail').should(
      'not.exist'
    );

  })

  it('Show account informations for a user', () => {
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

    cy.get('input[formControlName=email]').type("test@mail.com")
    cy.get('input[formControlName=password]').type(`${"password"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    cy.intercept('GET', '/api/user/1', {
      body: {
        id: 2,
        username: 'test',
        firstName: 'test',
        lastName: 'test',
        email: 'test@mail.com',
        admin: false
      },
    })
    cy.get('[routerlink="me"]').click();
    cy.url().should('include', '/me');
    cy.contains('p', 'Name: test TEST').should('exist');
    cy.contains('p', 'Email: test@mail.com').should('exist');
    cy.contains('p.my2', 'You are admin').should('not.exist');
    cy.contains('p', 'Delete my account:').should('exist');
    cy.contains('button.mat-raised-button.mat-warn', 'Detail').should(
      'exist'
    );
  });

  it('Should delete account when the button is clicked', () => {
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

    cy.get('input[formControlName=email]').type("test@mail.com")
    cy.get('input[formControlName=password]').type(`${"password"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    cy.intercept('GET', '/api/user/1', {
      body: {
        id: 2,
        username: 'test',
        firstName: 'test',
        lastName: 'test',
        email: 'test@mail.com',
        admin: false
      },
    })
    cy.get('[routerlink="me"]').click();
    cy.url().should('include', '/me');
    cy.intercept('DELETE', '/api/user/1', {
      status: 200
    });
    cy.contains('delete').click();
  });

  it('should redirect to login if the user in not logged', () => {
    cy.visit('/me');
    cy.url().should('include', '/login');
  });
});
