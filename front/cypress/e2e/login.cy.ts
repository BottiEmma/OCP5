describe('Login spec', () => {
  beforeEach(() => {
    cy.visit('/register');
  });
  it('should go to login page when login is clicked', () => {
    cy.contains('Login').click();

    cy.url().should('include', '/login');
  });
  it('Login successfull', () => {
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
  })

  it('should not login with bad credentials', () => {
    cy.visit('/login');
    cy.get('input[formControlName=email]').type("yoga@studio.com");
    cy.get('input[formControlName=password]').type(`${"wrongpassword"}{enter}{enter}`);
    cy.url().should('not.contain', '/sessions');
    cy.get('.error').should('be.visible');
  });
});
