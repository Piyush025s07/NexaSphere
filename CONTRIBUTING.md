# Contributing to NexaSphere

Thank you for your interest in contributing to NexaSphere! We welcome contributions from the community to help improve our platform. This document provides guidelines and standards to ensure smooth collaboration.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Contribution Workflow](#contribution-workflow)
- [Branch Naming Conventions](#branch-naming-conventions)
- [Commit Message Conventions](#commit-message-conventions)
- [Pull Request Guidelines](#pull-request-guidelines)
- [Code Style and Quality Standards](#code-style-and-quality-standards)
- [Testing Requirements](#testing-requirements)
- [Review Process](#review-process)
- [Repository Standards](#repository-standards)
- [Issue Guidelines](#issue-guidelines)
- [Do's and Don'ts](#dos-and-donts)

## Code of Conduct

This project follows a code of conduct to ensure a welcoming environment for all contributors. By participating, you agree to:

- Be respectful and inclusive
- Focus on constructive feedback
- Accept responsibility for mistakes
- Show empathy towards other contributors
- Help create a positive community

## Getting Started

### Prerequisites

Before contributing, ensure you have:

- Node.js 20+
- Java JDK 17+
- Maven 3.8+
- Python 3.11+
- Git

### Local Setup

1. Fork the repository
2. Clone your fork: `git clone https://github.com/YOUR_USERNAME/nexasphere.git`
3. Navigate to the project: `cd nexasphere`
4. Follow the setup instructions in the [README.md](README.md)

## Contribution Workflow

1. **Choose an Issue**: Look for issues labeled `good first issue`, `help wanted`, or `bug`
2. **Create a Branch**: Use the appropriate branch naming convention
3. **Make Changes**: Implement your solution following our coding standards
4. **Test Thoroughly**: Ensure all tests pass and functionality works
5. **Commit Changes**: Use clear, descriptive commit messages
6. **Push to Branch**: Push your changes to your fork
7. **Create Pull Request**: Submit a PR with detailed description
8. **Address Feedback**: Respond to reviewer comments and make necessary changes
9. **Merge**: Once approved, your changes will be merged

## Branch Naming Conventions

Use descriptive, lowercase branch names with hyphens:

- **Features**: `feature/add-user-authentication`
- **Bug Fixes**: `fix/resolve-responsive-layout`
- **Documentation**: `docs/update-api-documentation`
- **Hotfixes**: `hotfix/critical-security-patch`

Examples:
- `feature/implement-dark-mode`
- `fix/navbar-mobile-bug`
- `docs/add-contribution-guide`

## Commit Message Conventions

Follow conventional commit format:

```
type(scope): description

[optional body]

[optional footer]
```

### Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

### Examples

```
feat: add user authentication system
fix: resolve mobile navigation menu bug
docs: update API documentation for events endpoint
style: format code with prettier
refactor: simplify event validation logic
test: add unit tests for user service
```

## Pull Request Guidelines

### PR Requirements

- **Title**: Clear, descriptive title following commit conventions
- **Description**: Detailed explanation of changes
- **Linked Issues**: Reference related issues with `#issue-number`
- **Testing**: Describe how changes were tested
- **Screenshots**: Include before/after screenshots for UI changes
- **Checklist**: Complete all items in the PR template

### PR Template

Use the provided PR template in `.github/pull_request_template.md`

## Code Style and Quality Standards

### General Guidelines

- Write clear, readable, and maintainable code
- Add comments for complex logic
- Use meaningful variable and function names
- Keep functions under 40 lines when possible
- Remove all `console.log` statements before committing

### Frontend (React/Vite)

- Use functional components with hooks
- Follow React best practices
- Use TypeScript for type safety (where applicable)
- Maintain consistent CSS class naming
- Ensure responsive design

### Backend (Java/Spring Boot)

- Follow Spring Boot conventions
- Use proper exception handling
- Implement validation for all inputs
- Write comprehensive unit tests
- Use meaningful package structure

### Backend (Python/FastAPI)

- Follow PEP 8 style guide
- Use type hints
- Write docstrings for functions
- Handle exceptions properly
- Use async/await for I/O operations

## Testing Requirements

### Before Submitting a PR

- **Unit Tests**: All new code must have unit tests
- **Integration Tests**: Test API endpoints and integrations
- **Manual Testing**: Verify functionality works as expected
- **Cross-browser Testing**: Test on major browsers
- **Mobile Testing**: Ensure responsive design works

### Running Tests

```bash
# Frontend
npm test

# Java Backend
mvn test

# Python Service
pytest
```

### Test Coverage

Maintain or improve test coverage for all changes.

## Review Process

### Timeline

- **Initial Review**: Within 2-3 business days
- **Feedback Response**: Address comments within 1 week
- **Re-review**: Within 2 business days after updates
- **Merge**: Approved PRs merged within 1 business day

### Review Criteria

- Code quality and style
- Test coverage
- Documentation
- Functionality verification
- Security considerations
- Performance impact

## Repository Standards

### Protected Branches

- `main`: Protected branch, requires PR approval
- `develop`: Protected branch for development
- No direct pushes allowed to protected branches

### CI/CD Expectations

- All PRs must pass CI checks
- Code must build successfully
- Tests must pass
- Linting must pass
- Security scans must pass

### Build Requirements

- Frontend: `npm run build` succeeds
- Java: `mvn clean install` succeeds
- Python: All dependencies install correctly

### Review Requirements

- At least 1 maintainer review required
- All CI checks must pass
- No unresolved conversations
- Approved by code owner if applicable

## Issue Guidelines

### Issue Labels

- `good first issue`: Suitable for new contributors
- `bug`: Bug reports
- `feature`: New feature requests
- `documentation`: Documentation improvements
- `help wanted`: Community help needed
- `enhancement`: Improvements to existing features
- `question`: Questions or discussions

### Creating Issues

- Use clear, descriptive titles
- Provide detailed description
- Include steps to reproduce (for bugs)
- Add relevant labels
- Link related issues/PRs

### Issue Templates

Use the provided issue templates for:
- Bug reports
- Feature requests
- Documentation updates

## Do's and Don'ts

### Do's ✅

- Follow all guidelines in this document
- Test your changes thoroughly
- Write clear commit messages
- Provide detailed PR descriptions
- Respond promptly to feedback
- Ask questions if unsure
- Keep PRs focused on single issues
- Update documentation for changes

### Don'ts ❌

- Don't commit directly to main/develop
- Don't submit untested code
- Don't ignore reviewer feedback
- Don't make unrelated changes in a PR
- Don't use vague commit messages
- Don't leave console.log statements
- Don't submit large PRs without discussion
- Don't duplicate existing issues

## Getting Help

- Check existing issues and documentation first
- Use GitHub Discussions for questions
- Join our community channels
- Contact maintainers for guidance

Thank you for contributing to NexaSphere! 🎉