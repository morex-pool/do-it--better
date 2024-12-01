# Do It Better

**Name**: do-it-better

**Description**: An IntelliJ IDEA plugin designed to enhance code implementation through analysis, documentation, and improvement suggestions.

**Note:** This project is for educational purposes but aims to provide practical value in real-world development 
scenarios.
---

## Overview

This project aims to develop an educational yet potentially useful plugin for IntelliJ IDEA. Here's what we're planning:

- **Development of an IntelliJ IDEA Plugin**
    - Analyze code quality and complexity to make it better
    - Offer suggestions for improvement
    - Enhance code documentation and traceability

## Key Features

1. **Code Analysis**
    - Scan the current file or entire project for issues.
    - Categorize and report issues in a tree structure within an IntelliJ IDEA JPanel.
        - Each issue can:
            - Open a side panel explaining the issue and suggesting best practices.
            - Navigate directly to the code where the issue occurs.

2. **Advanced Analysis Capabilities**
    - **Class Hierarchy Mapping**: Create a map or hierarchy of classes.
    - **Method Flow Analysis**: Show sequence flow of methods.
    - **Impact Analysis**: Highlight side effects of code changes on other methods or tests.
    - **Complexity Suggestion**: Suggest improvements for reducing code complexity.
    - **Automated Testing Suggestions**:
        - Suggest writing both positive and negative test cases for unit testing.
        - Identify and report on bottlenecks within tested methods.
    - **Documentation Automation**: Generate documentation for methods and classes, explaining:
        - Their functionality
        - Dependencies
        - Usages
        - Input and output sample

3. **Future feature: Integration with Issue Tracking**
    - Analyze legacy code to:
        - Create tickets in issue trackers with explanations and fix suggestions.
        - Propose new code implementations:
            - Generate new code based on old code concepts and requirements.
            - Create pull requests, update related documentation, and handle deprecation.

4. **AI Assistance**
    - Optionally connect to a small Language Model (LLM) application for enhanced analysis and suggestions tailored to your coding style or project needs.

## Installation

- Clone this repository:
  ```bash
  git clone https://github.com/morex-pool/do-it-better.git

## Contributing
**Contributions are welcome!** Please fork the repository and submit pull requests with your improvements or feature additions.

---
